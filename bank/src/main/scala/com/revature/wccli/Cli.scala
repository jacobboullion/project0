package com.revature.wccli

import scala.io.StdIn
import scala.util.matching.Regex

class Cli {

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


    //prints welcome 
    def printWelcome(): Unit = {
        println("Welcome to word count CLI!")
    }

    //print commands from the user
    def printOptions(): Unit = {
        println("commands available")
        println("echo [string to repeat] : repeats a string back to the user")
    }

    //runs the menu for CLI
    def menu() : Unit = {
        printWelcome()
        printOptions()

        val input = StdIn.readLine()
        
        input match {
            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("echo") => {
                println(arg)
            }

            case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("exit") => {
                System.exit(0)
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