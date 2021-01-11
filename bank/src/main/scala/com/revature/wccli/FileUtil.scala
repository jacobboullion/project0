package com.revature.wccli

import scala.io.BufferedSource
import scala.io.Source
import java.io.File

object FileUtil {

    def getTextContent(filename: String, sep: String = " "): String = {
        //We open files usin Source.fromFile, this will give us a bufferSource.
        //remember to close file and deal with exceptions
        var openedFile : BufferedSource = null //declare our open file 
        try{
            openedFile = Source.fromFile(filename)
            // Scala returns the last line of this try block
            openedFile.getLines().mkString(" ")
        } finally{
            if(openedFile != null) openedFile.close()
        }
    }

    def getTopLevelFiles()= {
        val f = new File(".")
        f.listFiles().filter(_.isFile()).map(_.getName()).mkString(", ")
    }
}