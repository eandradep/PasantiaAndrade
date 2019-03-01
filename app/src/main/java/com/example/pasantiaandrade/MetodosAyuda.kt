package com.example.pasantiaandrade

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.Serializable
import java.util.*

class MetodosAyuda (var context: Context) {

    fun bitmapToFile(bitmap:Bitmap,Nombre:String){
        val fos: FileOutputStream
        try {
            fos = context.openFileOutput(Nombre, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d("Error:", "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d("Error:", "io exception")
            e.printStackTrace()
        }
    }

    fun buscarFoto(Nombre:String):Bitmap {
        var b: Bitmap? = null
        val fis: FileInputStream
        try {
            fis = context.openFileInput(Nombre)
            b = BitmapFactory.decodeStream(fis) as Bitmap
            fis.close()

        } catch (e: FileNotFoundException) {
            Log.d("Error", "file not found")
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d("Error", "io exception")
            e.printStackTrace()
        }
        return b!!
    }


    fun calcularEdad(fecha:Calendar): String {
        val fechaActual = Calendar.getInstance()
        var years = fechaActual.get(Calendar.YEAR) - fecha.get(Calendar.YEAR)
        val months = fechaActual.get(Calendar.MONTH) - fecha.get(Calendar.MONTH)
        val days = fechaActual.get(Calendar.DAY_OF_MONTH) - fecha.get(Calendar.DAY_OF_MONTH)
        if (months < 0 || months == 0 && days < 0) {
            years--
        }
        return years.toString()
    }

    fun addBundleFragment(anyFragment: Fragment, serializable: Serializable?, clave: String): Fragment {
        val args = Bundle()
        args.putSerializable(clave,serializable)
        anyFragment.arguments=args
        return anyFragment
    }

}