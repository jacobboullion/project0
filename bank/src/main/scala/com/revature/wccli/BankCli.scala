package com.revature.wccli


import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
import scala.collection.mutable.ArrayBuffer
import com.revature.wccli.daos.PersonDao
import com.revature.wccli.model.Person
//Options for command line and file computution will be done here

class BankCli {

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


    //options 
    def printOptions() : Unit ={
        
        println("***commands available***")
        println("exit [exits the program]: command line will stop")
        println("printExp [filename]: prints expenses")
        println("printToTable [filename]: prints expenses to a database")
        println("printExp [filename]: prints expenses")
        println("add person: adds person")
        println("update person: update expense of a person")
        println("delete person: deletes a person")
        println("print expenses: prints expenses")
        //println("subForty [filename] : subtracts $40 from all expenses")

    }

    //menu for Cli
    def menu() : Unit = {

        var menuLoop : Boolean = true 

        while(menuLoop) {

          printOptions()
          val input = StdIn.readLine()

          input match {

            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("printExp") => {
                printExp(arg)
            }

            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("printToTable") => {
                printExpToTable(arg)
            }

            case commandArgPattern(cmd, arg)
            if cmd.equalsIgnoreCase("print") && arg.equalsIgnoreCase("expenses") => {
                printAllPeople()
            }

            case commandArgPattern(cmd, arg)
            if cmd.equalsIgnoreCase("add") && arg.equalsIgnoreCase("person") => {
                runAddPersonSubMenu()
            }

            case commandArgPattern(cmd, arg)
            if cmd.equalsIgnoreCase("update") && arg.equalsIgnoreCase("person") => {
                runUpdatePersonSubMenu()
            }

            case commandArgPattern(cmd, arg)
            if cmd.equalsIgnoreCase("delete") && arg.equalsIgnoreCase("person") => {
                runDeletePersonSubMenu()
            }

            //case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("subForty") => {
            //    addMoney(arg)
            //}

            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("exit") => {
                menuLoop = false
            }

            case commandArgPattern(cmd, arg) => {
                println(s"Failed to parse command: $cmd with arguments: $arg")
            }

            case _ => {
                println("failed to parse any input")
            }
          }

        }


    }

    def printExp(arg: String) : Unit = {
        var expArray = ArrayBuffer[Array[String]]()
        try{
            expArray = FileExp.getExpenses(arg)
            //println(expArray(1)(0).toInt + 5)
            for (row <- expArray) {
        println(s"${row(0)}|${row(1)}|${row(2)}|${row(3)}")
        
    }
        } catch{
            case fnfe: FileNotFoundException => println(s"Failed to find file: ${fnfe.getMessage()}")
        }
    } 

    def printExpToTable(arg: String) : Unit = {
        var expArray = ArrayBuffer[Array[String]]()
        try{
            expArray = FileExp.getExpenses(arg)
            for (i <- 0 until expArray.length) {
                PersonDao.saveNew(Person(expArray(i)(0).toInt, expArray(i)(1), expArray(i)(2), expArray(i)(3).toInt))
        
    }
        } catch{
            case fnfe: FileNotFoundException => println(s"Failed to find file: ${fnfe.getMessage()}")
        }
    } 

  def printAllPeople(): Unit = {
    PersonDao.getAll().foreach(println)
  }

  def printFirstName(first_name: String): Unit = {
    PersonDao.get(first_name).foreach(println)
  }
  
  /**
    * runs an add book sub menu, we're skipping some QoL features present on the main menu
    */
  def runAddPersonSubMenu(): Unit = {
    println("First Name?")
    val firstInput = StdIn.readLine()
    println("Last Name?")
    val lastInput = StdIn.readLine()
    println("Expense?")
    val expInput = StdIn.readLine().toInt
    try {
      if (PersonDao.saveNew(Person(0, firstInput, lastInput, expInput))) {
        println("added new Person!")
      } 
    } catch {
      case e : Exception => {
        println("failed to add Person.")
      }
    }
  }

  def runUpdatePersonSubMenu(): Unit = {
    println("Expense?")
    val expInput = StdIn.readLine().toInt
    println("The user_id of the expense you want to change?")
    val idInput = StdIn.readLine().toInt
    try {
      if (PersonDao.updateExpense(Person(idInput, "","", expInput))) {
        println(s"updated expense of id = $idInput!")
      } 
    } catch {
      case e : Exception => {
        println("failed to update expense.")
        
      }
    }
  }

  def runDeletePersonSubMenu(): Unit = {
    println("The user_id of the person you want to delete?")
    val idInput = StdIn.readLine().toInt
    try {
      if (PersonDao.deleteBook(Person(idInput, "", "", 0))) {
        println(s"Deleted person with id = $idInput!")
      } else {
        println(s"Person with id = $idInput does not exist")
      }
    } catch {
      case e : Exception => {
        println("failed to delete person.")
        
      }
    }
  }


    /*def addMoney(arg: String) : Unit = {
        try{
            val array : Array[Double] = FileExp.getExpenses(arg)
            array.foreach({ case (expense) => 
                if (expense > 40d){
                    println(expense - 40d)
                }else{println(0d)}
            })
        } catch{
            case fnfe: FileNotFoundException => println(s"Failed to find file: ${fnfe.getMessage()}")
        }
    }*/
}