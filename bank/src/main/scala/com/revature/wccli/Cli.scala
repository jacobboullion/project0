package com.revature.wccli

import scala.io.StdIn
import scala.util.matching.Regex
import java.io.FileNotFoundException

class Cli {

    val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r


    //prints welcome 
    def printWelcome(): Unit = {
        println("Welcome to word count CLI!")
    }

    //print commands from the user
    def printOptions(): Unit = {
        println("commands available")
        println("exit [exits the program]: command line will stop")
        println("Bonus Comand: ")
        println("echo [string to repeat] : repeats a string back to the user")
        println("addFive [number] : returns the given number plus 5")
        println("printTextContent [filename]: print contents of filename")
        println("wordcount [filename]")
    }

    //runs the menu for CLI
    def menu() : Unit = {
        printWelcome()
        var continueMenuLoop = true

        while (continueMenuLoop) { 
            printOptions()

            val input = StdIn.readLine()
            
            input match {
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("wordcount") => {
                    wordcount(arg)
                }
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("echo") => {
                    println(arg)
                }

                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("exit") => {
                    continueMenuLoop = false
                }

                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("addfive") => {
                    addFive(arg)

                }

                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("printtextcontent") => {
                    try{
                        println(FileUtil.getTextContent(arg))
                    } catch{
                        case fnfe: FileNotFoundException => println(s"Failed to find file: ${fnfe.getMessage()}")
                        println(s"found top level files ${FileUtil.getTextContent(arg)}")
                    }
                }

                case commandArgPattern(cmd, arg) => {
                    println(s"Failed to parse command: $cmd with arguments: $arg")
                }

                case _ => {
                    println("failed to parse any input")
                }

            }
        }
        println("thank you for using word count cli goodbye")
    }

    def addFive(arg: String) = {
        try{
            println(s"result: ${arg.toDouble + 5.0}")
        } catch {
            case nfe: NumberFormatException => {
                println(s"""Failed to parse "$arg" as a double""")
                println(s"exeception message: ${nfe.getMessage}")
            }
        }
    }

    def wordcount(arg: String) ={
        try{
            val text = FileUtil.getTextContent(arg)
            
            text.replaceAll("\\p{Punct}", "")
                .toLowerCase()
                .split("\\s+")
                .take(100)
                .groupMapReduce(word => word)(word => 1)(_ + _)
                .toSeq
                .sortBy( {case (word, count) => count})
                .foreach{case (word,count) => println(s"$word, $count")}
                
            
            //group reduce !


        } catch{
            case fnfe: FileNotFoundException => {
                println(s"Failed to find file: ${fnfe.getMessage()}")
            
            println(s"found top level files ${FileUtil.getTopLevelFiles}")
            } 
        }
    }

}