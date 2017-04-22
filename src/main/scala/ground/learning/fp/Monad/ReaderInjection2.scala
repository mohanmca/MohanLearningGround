package ground.learning.fp.Monad

import java.sql.DriverManager
import java.sql.Connection
import scala.Function1

class ReaderInjection2 {

  case class DB[A](g : Connection => A) {
    def apply(c : Connection) = g(c)
    def map[B](f : A => B) : DB[B] = DB(c => f(g(c)))
    def flatMap[B](f : A => DB[B]) : DB[B] = DB { (c : Connection) => f(g(c)).apply(c) }
  }

  implicit def convert[A](g : Connection => A) : DB[A] = DB(g)

  def getPassword(userId : String) : Connection => String = c =>
    {
      c.createStatement().executeQuery("select password from user where userId=${userId}").getString("password")
    }

  def setPassword(userId : String, newPassword : String) : Connection => Boolean = c =>
    {
      val stmt = c.prepareStatement("update user set password=${newPassword} where userId=${userId}")
      stmt.setString(1, userId)
      stmt.setString(2, newPassword)
      stmt.execute()
    }

  def changePwd(userId : String, newPassword : String) =
    for {
      password <- getPassword(userId)
      result <- setPassword(userId, newPassword)
    } yield result

  abstract class ConnectionProvider {
    def apply[A](g : DB[A]) : A
  }

  def mkConnectionProvider(driver : String, userId : String, password : String) = new ConnectionProvider {
    def apply[A](g : DB[A]) : A = {
      val conn = DriverManager.getConnection(driver, userId, password)
      g(conn)
    }
  }

  lazy val mySqlProvider = mkConnectionProvider("mysqldriver", "mysqluser", "mysqlpassword")

  lazy val hsqlProvider = mkConnectionProvider("mysqldriver", "mysqluser", "mysqlpassword")

  def runProgram(userId : String) = (r : ConnectionProvider) => {
    val newPwd = readLine()
    r(changePwd(userId, newPwd))
  }
  def runInTest[A](f : ConnectionProvider => A) = f(mySqlProvider)
  def runInProd[A](f : ConnectionProvider => A) = f(hsqlProvider)

  def main(args : Array[String]) : Unit = runInProd(runProgram(args(0)))

}