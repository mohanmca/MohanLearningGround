package ground.learning.fp.Monad

import java.sql.DriverManager
import org.eclipse.jetty.server.ConnectionFactory
import java.sql.Connection

class ReaderInjection {

  object ConnectionFactory {
    def getConnection : Connection = ???
  }
  //Sample1
  def setUserPwd1(id : String, pwd : String) = {
    Class.forName("org.sqlite.JDBC")
    val c = DriverManager.getConnection("jdbc:sqlite::memory:")
    val stmt = c.prepareStatement(
      "update users set pwd = ? where id = ?")
    stmt.setString(1, pwd)
    stmt.setString(2, id)
    stmt.executeUpdate
    c.commit
    c.close
  }

  //Improvement - Using global connection factory
  //Hidden dependency.
  //Requires magic initialization step.
  //Same goes for thread-local connections.
  def setUserPwd2(id : String, pwd : String) = {
    val c = ConnectionFactory.getConnection
    val stmt = c.prepareStatement(
      "update users set pwd = ? where id = ?")
    stmt.setString(1, pwd)
    stmt.setString(2, id)
    stmt.executeUpdate
    stmt.close
  }

  //Step3 - Passing parameter or Dependency Injection :)
  def setUserPwd3(id : String, pwd : String, c : Connection) = {
    val stmt = c.prepareStatement(
      "update users set pwd = ? where id = ?")
    stmt.setString(1, pwd)
    stmt.setString(2, id)
    stmt.executeUpdate
    stmt.close
  }

  //main(args: Array[String]) -> level1() -> level2() -> level3 -> level4 -> level5 -> level6(needs connection)

  // Do everything as if they are already available.. dependency becomes curried parameter..
  def setUserPwd(id : String, pwd : String) : Connection => Unit =
    c => {
      val stmt = c.prepareStatement(
        "update users set pwd = ? where id = ?")
      stmt.setString(1, pwd)
      stmt.setString(2, id)
      stmt.executeUpdate
      stmt.close
    }

  def getUserPwd(id : String) : Connection => String = c => {
    val stmt = c.prepareStatement(
      "select password from users where userid = ${id}")
    stmt.setString(1, id)
    stmt.executeQuery().getString("password")

  }

  // map : (A => B) => (DB[A] => DB[B])
  case class DB[A](g : Connection => A) {
    def apply(c : Connection) = g(c)
    def map[B](f : A => B) : DB[B] = DB(c => f(g(c)))
    def flatMap[B](f : A => DB[B]) : DB[B] = DB(c => f(g(c)).g(c))

  }

  def pure[A](a : A) : DB[A] = DB(c => a)
  implicit def convert[A](f : (Connection => A)) = DB[A](f)

  def changePwd(userid : String, oldPwd : String, newPwd : String) : DB[Boolean] =
    for {
      pwd <- getUserPwd(userid)
      eq <- if (pwd == oldPwd) for {
        _ <- setUserPwd(userid, newPwd)
      } yield true
      else pure(false)
    } yield eq

  abstract class ConnProvider {
    def apply[A](f : DB[A]) : A
  }

  def mkProvider(driver : String, url : String) : ConnProvider = new ConnProvider {
    def apply[A](f : DB[A]) : A = {
      Class.forName(driver)
      val conn =
        DriverManager.getConnection(url)
      try { f(conn) }
      finally { conn.close }
    }
  }

  lazy val sqliteTestDB =
    mkProvider("org.sqlite.JDBC", "jdbc:sqlite::memory:")

  lazy val mysqlProdDB =
    mkProvider("org.gjt.mm.mysql.Driver", "jdbc:mysql://prod:3306/?user=one&password=two")

  def myProgram(userid : String) : ConnProvider => Unit =
    r => {
      println("Enter old password")
      val oldPwd = readLine
      println("Enter new password")
      val newPwd = readLine
      r(changePwd(userid, oldPwd, newPwd))
    }

  def runInTest[A](f : ConnProvider => A) : A = f(sqliteTestDB)
  def runInProduction[A](f : ConnProvider => A) : A = f(mysqlProdDB)
  def main(args : Array[String]) = runInTest(myProgram(args(0)))

}