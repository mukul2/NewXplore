package com.rafiq.newxplore.utlis

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.InetAddress
import java.io.*

class MyFunctions {
     fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context. getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("www.google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    fun cacheData(filename: String, data: String, context: Context) {


        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        } catch (e: FileNotFoundException) {
            Log.i("mkl", e.localizedMessage)

            e.printStackTrace()
        } catch (e: NumberFormatException) {
            Log.i("mkl", e.localizedMessage)

            e.printStackTrace()
        } catch (e: IOException) {
            Log.i("mkl", e.localizedMessage)

            e.printStackTrace()
        } catch (e: Exception) {
            Log.i("mkl", e.localizedMessage)
            e.printStackTrace()
        }

    }

    fun retriveData(filename: String, context: Context): String {


        var fileInputStream: FileInputStream? = null
        fileInputStream = context.openFileInput(filename)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ({ text = bufferedReader.readLine(); text }() != null) {
            stringBuilder.append(text)
        }
        //Displaying data on EditText
        Log.i("mkl", "read data => " + stringBuilder.toString().toString())
        return stringBuilder.toString().toString()

    }
}