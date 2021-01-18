package com.revature.wccli.model

import java.sql.ResultSet

case class Person (user_id: Int, first_name: String, last_name: String, expense: Int){}


object Person {

    def fromResultSet (rs: ResultSet) : Person = {

        apply(
            rs.getInt("user_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getInt("expense"),
        )
    }
}