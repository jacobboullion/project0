package com.revature.wccli

import scala.io.BufferedSource
import scala.io.Source
import java.io.File
import scala.collection.mutable.ArrayBuffer

object FileExp {

    def getExpenses (filename: String, sep: String = " ") : ArrayBuffer[Array[String]] = {

        var openedFile : BufferedSource = null
        val rows = ArrayBuffer[Array[String]]()

        try{
            openedFile = Source.fromFile(filename)

            for (line <- openedFile.getLines) {
                rows += line.split(",").map(_.trim)
            }

            rows
            //openedFile.getLines().mkString(" ")
            
        }finally{
            if(openedFile != null) openedFile.close()
        }
    }

}