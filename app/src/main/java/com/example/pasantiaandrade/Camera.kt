package com.example.pasantiaandrade

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
class Camera (var context: Context) {

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

    fun Buscar_Foto(Nombre:String):Bitmap {
        var b: Bitmap? = null
        val fis: FileInputStream
        try {
            fis = context.openFileInput(Nombre)
            b = BitmapFactory.decodeStream(fis) as Bitmap
            fis.close()

        } catch (e: FileNotFoundException) {
            Log.d("Error", "file not found");
            e.printStackTrace()
        } catch (e: IOException) {
            Log.d("Error", "io exception");
            e.printStackTrace()
        }
        return b!!
    }
}