package com.revature.wccli

import scala.io.BufferedSource
import scala.io.Source
import java.io.File

object FileExp {

    def getExpenses (filename: String, sep: String = " ") : Array[Double] = {

        var openedFile : BufferedSource = null
        //var array : Array[Double] = Array.empty

        try{
            openedFile = Source.fromFile(filename)
            val array : Array[Double] = openedFile.getLines().flatMap(_.split(",")).map(_.toDouble).toArray
            return array

            //openedFile.getLines().mkString(" ")
            
        }finally{
            if(openedFile != null) openedFile.close()
        }
    }

}