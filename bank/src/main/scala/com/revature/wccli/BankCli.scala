package com.revature.wccli


import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException
//Options for command line and file computution will be done here

class BankCli {

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


    //options 
    def printOptions() : Unit ={
        
        println("***commands available***")
        println("exit [exits the program]: command line will stop")
        println("printExp [filename]: prints expenses")
        println("subForty [filename] : subtracts $40 from all expenses")

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

                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("subForty") => {
                    addMoney(arg)
                }

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
        try{
            FileExp.getExpenses(arg).foreach{println}
        } catch{
            case fnfe: FileNotFoundException => println(s"Failed to find file: ${fnfe.getMessage()}")
        }
    } 

    def addMoney(arg: String) : Unit = {
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
    }
}