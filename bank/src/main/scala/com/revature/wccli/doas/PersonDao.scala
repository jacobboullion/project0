package com.revature.wccli.daos

import com.revature.wccli.model.Person
import com.revature.wccli.utils.ConnectionUtil
import scala.util.Using
import scala.collection.mutable.ArrayBuffer
import java.sql.Connection

/** A Book Data Access Object.  BookDao has CRUD methods for Books
  *
  * It allows us to keep all of our database access logic in this file,
  * while still allowing the rest of our application to use Books
  * retrieved from the database.
  */
object PersonDao {

  /** Retrieves all Books from the book table in the db
    *
    * @return
    */
  def getAll(): Seq[Person] = {
    val conn = ConnectionUtil.getConnection();
    Using.Manager { use =>
      val stmt = use(conn.prepareStatement("SELECT * FROM project0.expenses;"))
      stmt.execute()
      val rs = use(stmt.getResultSet())
      // lets use an ArrayBuffer, we're adding one element at a time
      val allPeople: ArrayBuffer[Person] = ArrayBuffer()
      while (rs.next()) {
        allPeople.addOne(Person.fromResultSet(rs))
      }
      allPeople.toList
    }.get
    // the .get retrieves the value from inside the Try[Seq[Book]] returned by Using.Manager { ...
    // it may be better to not call .get and instead return the Try[Seq[Book]]
    // that would let the calling method unpack the Try and take action in case of failure
  }

  def get(first_name: String): Seq[Person] = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager { use =>
      val stmt = use(conn.prepareStatement("SELECT * FROM project0.expenses WHERE first_name = ?"))
      stmt.setString(1, first_name)
      stmt.execute()
      val rs = use(stmt.getResultSet())
      val firstName : ArrayBuffer[Person] = ArrayBuffer()
      while (rs.next()) {
        firstName.addOne(Person.fromResultSet(rs))
      }
      firstName.toList
    }.get
  }


  def saveNew(person : Person) : Boolean = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager { use =>
      val stmt = use(conn.prepareStatement("INSERT INTO project0.expenses VALUES (DEFAULT, ?, ?, ?);"))
      stmt.setString(1, person.first_name)
      stmt.setString(2, person.last_name)
      stmt.setInt(3, person.expense)
      stmt.execute()
      //check if rows were updated, return true is yes, false if no
      stmt.getUpdateCount() > 0
    }.getOrElse(false)
    // also returns false if a failure occurred
  }

  def updateExpense(person : Person) : Boolean = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager {use => 
      val stmt = use(conn.prepareStatement("UPDATE project0.expenses SET  expense = ? where user_id = ?"))
      stmt.setInt(1, person.expense)
      stmt.setInt(2, person.user_id)
      stmt.execute()

      stmt.getUpdateCount() > 0

    }.getOrElse(false)
  }

  def deleteBook(person : Person) : Boolean = {
    val conn = ConnectionUtil.getConnection()
    Using.Manager {use => 
      val stmt = use(conn.prepareStatement("DELETE FROM project0.expenses WHERE user_id = ?"))
      stmt.setInt(1, person.user_id)
      stmt.execute()

      stmt.getUpdateCount() > 0

    }.getOrElse(false)

  }

}