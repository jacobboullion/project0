package com.revature.wccli.model

import java.sql.ResultSet

case class Book (book_id : Int, title: String, isbn: String){

}

object Book {
    def fromResultSet(rs : ResultSet) : Book ={

        apply(
            rs.getInt("book_id"),
            rs.getString("title"),
            rs.getString("isbn"),
        )
    }
}