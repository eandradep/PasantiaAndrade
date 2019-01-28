package com.example.pasantiaandrade.DBHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.pasantiaandrade.Model.UsuarioMT
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_APELLIDO
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_ID
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_IMAGEN
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_NOMBRE
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_PASSWORD
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_TELEFONO
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.COL_TIPO
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.DATABASE_NAME
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.DATABASE_VER
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_TABLE_NAME
import com.example.pasantiaandrade.Model.UsuarioNino
import com.sdsmdg.tastytoast.TastyToast
import java.lang.Exception


@SuppressLint("ByteOrderMark")
class DBHelper (private var context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {



    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE $USER_TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_NOMBRE TEXT,$COL_APELLIDO TEXT, $COL_TIPO TEXT,$COL_IMAGEN TEXT, $COL_TELEFONO TEXT, $COL_PASSWORD TEXT)")
        db.execSQL("CREATE TABLE ${DatosTablas.NINO_TABLE_NAME}(${DatosTablas.NINO_COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,${DatosTablas.NINO_COL_NOMBRE} TEXT, ${DatosTablas.NINO_COL_APELLIDO} TEXT, ${DatosTablas.NINO_COL_FECHA} TEXT,${DatosTablas.NINO_COL_IMAGEN} TEXT, ${DatosTablas.NINO_COL_TELEFONO} TEXT, ${DatosTablas.NINO_COL_DIRECCION} TEXT)")
        Log.d("Se Crea Tabla Nino","${DatosTablas.NINO_TABLE_NAME}")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS ${DatosTablas.NINO_TABLE_NAME}")
        onCreate(db)
    }

    /*
    * METODOS CRUD DE LA TABLA DE USUARIO
    * OBTENER TODOS LOS USUARIOS
    * OBTENER UN SOLO USUARIO
    * AGREGAR USUARIO
    * ACTUALIZAR USUARIO
    * ELIMINAR USUARIO*/

    val allusers: List<UsuarioMT>
        get() {
            Log.d("Cosultando","Datos")
            val lstUsuers = ArrayList<UsuarioMT>()
            val selectquery  = "SELECT * FROM $USER_TABLE_NAME"
            val db : SQLiteDatabase = this.writableDatabase
            val cursor : Cursor = db.rawQuery(selectquery, null)
            if (cursor.moveToFirst())
            {
                do{
                    val usuario = UsuarioMT()
                    usuario.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    usuario.nombre = cursor.getString(cursor.getColumnIndex(COL_NOMBRE))
                    usuario.apellido = cursor.getString(cursor.getColumnIndex(COL_APELLIDO))
                    usuario.tipo = cursor.getString(cursor.getColumnIndex(COL_TIPO))
                    usuario.imagen = cursor.getString(cursor.getColumnIndex(COL_IMAGEN))
                    usuario.telefono = cursor.getString(cursor.getColumnIndex(COL_TELEFONO))
                    usuario.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                    lstUsuers.add(usuario)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstUsuers

        }

    fun getTask(_id: String, tipo:String): UsuarioMT {
        val usuario = UsuarioMT()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USER_TABLE_NAME WHERE $COL_PASSWORD == '$_id' AND $COL_TIPO == '$tipo'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {
                usuario.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                usuario.nombre = cursor.getString(cursor.getColumnIndex(COL_NOMBRE))
                usuario.apellido = cursor.getString(cursor.getColumnIndex(COL_APELLIDO))
                usuario.tipo = cursor.getString(cursor.getColumnIndex(COL_TIPO))
                usuario.imagen = cursor.getString(cursor.getColumnIndex(COL_IMAGEN))
                usuario.telefono = cursor.getString(cursor.getColumnIndex(COL_TELEFONO))
                usuario.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario
    }

    fun addUser(usuarioMT:UsuarioMT){
        val db : SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_NOMBRE,  usuarioMT.nombre)
        values.put(COL_APELLIDO,  usuarioMT.apellido)
        values.put(COL_TIPO,  usuarioMT.tipo)
        values.put(COL_IMAGEN,  usuarioMT.imagen)
        values.put(COL_TELEFONO,  usuarioMT.telefono)
        values.put(COL_PASSWORD,  usuarioMT.password)
        db.insert(USER_TABLE_NAME, null, values)
        TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        db.close()
    }

    fun updateUser(usuarioMT:UsuarioMT):Int {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,  usuarioMT.id)
        values.put(COL_NOMBRE,  usuarioMT.nombre)
        values.put(COL_APELLIDO,  usuarioMT.apellido)
        values.put(COL_TIPO,  usuarioMT.tipo)
        values.put(COL_IMAGEN,  usuarioMT.imagen)
        values.put(COL_TELEFONO,  usuarioMT.telefono)
        values.put(COL_PASSWORD,  usuarioMT.password)

        return db.update(USER_TABLE_NAME,values,"$COL_ID = ?", arrayOf(usuarioMT.id.toString()))

    }

    fun deleteUser(usuarioMT:UsuarioMT) {
        val db:SQLiteDatabase = this.writableDatabase
        db.delete(USER_TABLE_NAME, "$COL_ID=?", arrayOf(usuarioMT.id.toString()))
        db.close()
    }

    /*
    * METODOS CRUD DE LA TABLA DE USUARIO
    * OBTENER TODOS LOS USUARIOS
    * OBTENER UN SOLO USUARIO
    * AGREGAR USUARIO
    * ACTUALIZAR USUARIO
    * ELIMINAR USUARIO*/

    val allNino: List<UsuarioNino>
        get() {
            val lstNinos = ArrayList<UsuarioNino>()
            val selectquery  = "SELECT * FROM ${DatosTablas.NINO_TABLE_NAME}"
            val db : SQLiteDatabase = this.writableDatabase
            val cursor : Cursor = db.rawQuery(selectquery, null)
            if (cursor.moveToFirst())
            {
                do{
                    val nino = UsuarioNino()
                    nino.id = cursor.getInt(cursor.getColumnIndex(DatosTablas.NINO_COL_ID))
                    nino.nombre = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_NOMBRE))
                    nino.apellido = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_APELLIDO))
                    nino.fecha_nacimiento = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_FECHA))
                    nino.imagen = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_IMAGEN))
                    nino.telefono = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_TELEFONO))
                    nino.direccion = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_DIRECCION))
                    lstNinos.add(nino)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstNinos

        }

    fun getTaskNino(_id: Int): UsuarioNino {
        val usuario = UsuarioNino()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM ${DatosTablas.NINO_TABLE_NAME} WHERE ${DatosTablas.NINO_COL_ID} == '$_id'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {
                usuario.id = cursor.getInt(cursor.getColumnIndex(DatosTablas.NINO_COL_ID))
                usuario.nombre = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_NOMBRE))
                usuario.apellido = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_APELLIDO))
                usuario.fecha_nacimiento = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_FECHA))
                usuario.imagen = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_IMAGEN))
                usuario.telefono = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_TELEFONO))
                usuario.direccion = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_DIRECCION))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario
    }

    fun addNino(usuarioMaster: UsuarioNino){
        try {
            val db : SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(DatosTablas.NINO_COL_NOMBRE,  usuarioMaster.nombre)
            values.put(DatosTablas.NINO_COL_APELLIDO,  usuarioMaster.apellido)
            values.put(DatosTablas.NINO_COL_FECHA,  usuarioMaster.fecha_nacimiento)
            values.put(DatosTablas.NINO_COL_IMAGEN,  usuarioMaster.imagen)
            values.put(DatosTablas.NINO_COL_TELEFONO,  usuarioMaster.telefono)
            values.put(DatosTablas.NINO_COL_DIRECCION,  usuarioMaster.direccion)
            db.insert(DatosTablas.NINO_TABLE_NAME, null, values)
            TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            db.close()
        }catch (e: Exception){
            TastyToast.makeText(context, "Error Ingresando", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }

    fun updateNino(usuarioNino: UsuarioNino):Int {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(DatosTablas.NINO_COL_ID,  usuarioNino.id)
        values.put(DatosTablas.NINO_COL_NOMBRE,  usuarioNino.nombre)
        values.put(DatosTablas.NINO_COL_APELLIDO,  usuarioNino.apellido)
        values.put(DatosTablas.NINO_COL_FECHA,  usuarioNino.fecha_nacimiento)
        values.put(DatosTablas.NINO_COL_IMAGEN,  usuarioNino.imagen)
        values.put(DatosTablas.NINO_COL_TELEFONO,  usuarioNino.telefono)
        values.put(DatosTablas.NINO_COL_DIRECCION,  usuarioNino.direccion)

        return db.update(DatosTablas.NINO_TABLE_NAME,values,"${DatosTablas.NINO_COL_ID} = ?", arrayOf(usuarioNino.id.toString()))

    }

    fun deleteUser(usuarioNino: UsuarioNino) {
        val db:SQLiteDatabase = this.writableDatabase
        db.delete(DatosTablas.NINO_TABLE_NAME, "${DatosTablas.NINO_COL_ID}=?", arrayOf(usuarioNino.id.toString()))
        db.close()
    }


}