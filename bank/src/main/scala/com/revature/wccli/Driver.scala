package com.revature.wccli


import java.sql.DriverManager
import javax.print.DocFlavor.STRING
import java.sql.ResultSet
import com.revature.wccli.model.Track
import com.revature.wccli.model.Book
import com.revature.wccli.utils.ConnectionUtil
import com.revature.wccli.cli.Cli
import scala.util.Using
import java.sql.Connection

/*
object Driver {
    def main(args: Array[String]) : Unit = {

        val cli = new Cli();
        cli.menu()
        //runDemo()
    }

    def runDemo() = {

        var conn : Connection = null;

        Using.Manager { use =>
            conn = use(ConnectionUtil.getConnection())
            //use a prepared statement to try to get tracks
            val demoStatement = use(conn.prepareStatement("SELECT * FROM track WHERE length(name) > ? AND name LIKE ?;"))
            demoStatement.setInt(1, 10)
            demoStatement.setString(2, "B%")
            demoStatement.execute()
            val resultSet = use(demoStatement.getResultSet())
            while (resultSet.next()) {
                println(
                Track.produceFromResultSet(resultSet)
                )
            }
        }

        val bookStatement = conn.prepareStatement("SELECT * FROM book WHERE title = ?;")
        bookStatement.setString(1, "the Dark Tower")
        bookStatement.execute()
        val rs = bookStatement.getResultSet()
        while (rs.next()) {
            println(Book.fromResultSet(rs))
        }

        val insertBookStatement = conn.prepareStatement("INSERT INTO book VALUES (DEFAULT, ?, ?);")
        insertBookStatement.setString(1, "New Book Title")
        insertBookStatement.setString(2, "1826393847364")
        insertBookStatement.execute()

        conn.close()
    }

}*/