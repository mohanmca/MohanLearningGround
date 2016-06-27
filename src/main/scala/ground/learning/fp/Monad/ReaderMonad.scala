package ground.learning.fp.Monad

import java.sql.Connection
import java.sql.DriverManager

//Practice..
class ReaderMonad {
  case class DB[A](g : Connection => A) {
    def apply(c : Connection) : A = g(c)
    def map[B](f : A => B) : DB[B] = DB(c => f(g(c)))
    def flatMap[B](f : A => DB[B]) : DB[B] = DB(c => f(g(c)).g(c))
  }

  def pure[A](a : A) : DB[A] = DB(c => a)

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

  def changePassword(userId : String, newPassword : String) = {
    for {
      password <- getPassword(userId)
      result <- setPassword(userId, newPassword)
    } yield result
  }

  abstract class ConnectionProvider {
    def apply[A](dba : DB[A]) : A
  }

  def mkConnectionProider(jdbcUrl : String, userId : String, password : String) = new ConnectionProvider {
    def apply[A](dba : DB[A]) : A = {
      val conn = DriverManager.getConnection(jdbcUrl, userId, password);
      dba.apply(conn)
    }
  }

  lazy val mySqlConnProvider = mkConnectionProider("mysqlurl", "user", "password")
  lazy val sqliteConnProvider = mkConnectionProider("mysqlliteurl", "user", "password")

  def runProgram(userId : String) : ConnectionProvider => Boolean = cp => {
    val oldPassword = readLine()
    val newPassword = readLine()
    cp(changePassword(userId, newPassword))
  }

  def runInTest[A](f : ConnectionProvider => A) = f(mySqlConnProvider)
  def runInProd[A](f : ConnectionProvider => A) = f(sqliteConnProvider)

  def main(args : Array[String]) : Unit = {
    runInProd(runProgram(args(0)))
  }

}


