package com.example.pasantiaandrade.DBHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.pasantiaandrade.Model.UsuarioMT
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_APELLIDO
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_ID
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_IMAGEN
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_NOMBRE
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_PASSWORD
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_TELEFONO
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_COL_TIPO
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.DATABASE_NAME
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.DATABASE_VER
import com.example.pasantiaandrade.DBHelper.DatosTablas.Companion.USER_TABLE_NAME
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.Model.UsuarioNino
import com.sdsmdg.tastytoast.TastyToast
import java.lang.Exception


@SuppressLint("ByteOrderMark")
class DBHelper (private var context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {



    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL("CREATE TABLE $USER_TABLE_NAME($USER_COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$USER_COL_NOMBRE TEXT,$USER_COL_APELLIDO TEXT, $USER_COL_TIPO TEXT,$USER_COL_IMAGEN TEXT, $USER_COL_TELEFONO TEXT, $USER_COL_PASSWORD TEXT)")
            db.execSQL("CREATE TABLE ${DatosTablas.NINO_TABLE_NAME}(${DatosTablas.NINO_COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,${DatosTablas.NINO_COL_NOMBRE} TEXT, ${DatosTablas.NINO_COL_APELLIDO} TEXT, ${DatosTablas.NINO_COL_FECHA} TEXT,${DatosTablas.NINO_COL_IMAGEN} TEXT, ${DatosTablas.NINO_COL_TELEFONO} TEXT, ${DatosTablas.NINO_COL_DIRECCION} TEXT)")
            db.execSQL("CREATE TABLE ${DatosTablas.VAL_TABLE_NAME}(${DatosTablas.VAL_COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${DatosTablas.VAL_COL_TM_FK} INTEGER,${DatosTablas.VAL_COL_TN_FK} INTEGER, ${DatosTablas.VAL_COL_FECHA} TEXT, ${DatosTablas.VAL_COL_Observacion} TEXT, FOREIGN KEY(${DatosTablas.VAL_COL_TM_FK}) REFERENCES $USER_TABLE_NAME($USER_COL_ID),FOREIGN KEY(${DatosTablas.VAL_COL_TN_FK}) REFERENCES ${DatosTablas.NINO_TABLE_NAME}(${DatosTablas.NINO_COL_ID}))")
            TastyToast.makeText(context, "Creacion de Tablas Correcta", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
        }catch (e: Exception){
            TastyToast.makeText(context, "Creacion de Tablas Incorrecta", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS ${DatosTablas.NINO_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatosTablas.VAL_TABLE_NAME}")
        onCreate(db)
    }

    /**
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
                    usuario.TM_Id = cursor.getInt(cursor.getColumnIndex(USER_COL_ID))
                    usuario.TM_Nombre = cursor.getString(cursor.getColumnIndex(USER_COL_NOMBRE))
                    usuario.TM_Apellido = cursor.getString(cursor.getColumnIndex(USER_COL_APELLIDO))
                    usuario.TM_Tipo = cursor.getString(cursor.getColumnIndex(USER_COL_TIPO))
                    usuario.TM_Imagen = cursor.getString(cursor.getColumnIndex(USER_COL_IMAGEN))
                    usuario.TM_Telefono = cursor.getString(cursor.getColumnIndex(USER_COL_TELEFONO))
                    usuario.TM_Password = cursor.getString(cursor.getColumnIndex(USER_COL_PASSWORD))
                    lstUsuers.add(usuario)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstUsuers

        }

    fun getTask(_id: String, tipo:String): UsuarioMT {
        val usuario = UsuarioMT()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USER_TABLE_NAME WHERE $USER_COL_PASSWORD == '$_id' AND $USER_COL_TIPO == '$tipo'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {
                usuario.TM_Id = cursor.getInt(cursor.getColumnIndex(USER_COL_ID))
                usuario.TM_Nombre = cursor.getString(cursor.getColumnIndex(USER_COL_NOMBRE))
                usuario.TM_Apellido = cursor.getString(cursor.getColumnIndex(USER_COL_APELLIDO))
                usuario.TM_Tipo = cursor.getString(cursor.getColumnIndex(USER_COL_TIPO))
                usuario.TM_Imagen = cursor.getString(cursor.getColumnIndex(USER_COL_IMAGEN))
                usuario.TM_Telefono = cursor.getString(cursor.getColumnIndex(USER_COL_TELEFONO))
                usuario.TM_Password = cursor.getString(cursor.getColumnIndex(USER_COL_PASSWORD))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario
    }

    fun ObtenerImagenMaster(_id: Int): String {
        val usuario = UsuarioMT()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USER_TABLE_NAME WHERE $USER_COL_ID == '$_id'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {

                usuario.TM_Imagen = cursor.getString(cursor.getColumnIndex(USER_COL_IMAGEN))
                cursor.moveToNext()
            }
        }
        cursor.close()
        Log.d("Mensaje","${ usuario.TM_Imagen.toString()}")
        return usuario.TM_Imagen.toString()
    }


    fun addUser(usuarioMT:UsuarioMT){
        val db : SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(USER_COL_NOMBRE,  usuarioMT.TM_Nombre)
        values.put(USER_COL_APELLIDO,  usuarioMT.TM_Apellido)
        values.put(USER_COL_TIPO,  usuarioMT.TM_Tipo)
        values.put(USER_COL_IMAGEN,  usuarioMT.TM_Imagen)
        values.put(USER_COL_TELEFONO,  usuarioMT.TM_Telefono)
        values.put(USER_COL_PASSWORD,  usuarioMT.TM_Password)
        db.insert(USER_TABLE_NAME, null, values)
        TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        db.close()
    }

    fun updateUser(usuarioMT:UsuarioMT):Int {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(USER_COL_ID,  usuarioMT.TM_Id)
        values.put(USER_COL_NOMBRE,  usuarioMT.TM_Nombre)
        values.put(USER_COL_APELLIDO,  usuarioMT.TM_Apellido)
        values.put(USER_COL_TIPO,  usuarioMT.TM_Tipo)
        values.put(USER_COL_IMAGEN,  usuarioMT.TM_Imagen)
        values.put(USER_COL_TELEFONO,  usuarioMT.TM_Telefono)
        values.put(USER_COL_PASSWORD,  usuarioMT.TM_Password)

        return db.update(USER_TABLE_NAME,values,"$USER_COL_ID = ?", arrayOf(usuarioMT.TM_Id.toString()))

    }

    fun deleteUser(usuarioMT:UsuarioMT) {
        val db:SQLiteDatabase = this.writableDatabase
        db.delete(USER_TABLE_NAME, "$USER_COL_ID=?", arrayOf(usuarioMT.TM_Id.toString()))
        db.close()
    }

    /**
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
                    nino.TN_id = cursor.getInt(cursor.getColumnIndex(DatosTablas.NINO_COL_ID))
                    nino.TN_Nombre = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_NOMBRE))
                    nino.TN_Apellido = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_APELLIDO))
                    nino.TN_Fecha_Nac = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_FECHA))
                    nino.TN_Imagen = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_IMAGEN))
                    nino.TN_Telefono = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_TELEFONO))
                    nino.TN_Direccion = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_DIRECCION))
                    lstNinos.add(nino)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstNinos

        }

    fun BuscarNinoID(_id: Int): UsuarioNino {
        val usuario = UsuarioNino()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM ${DatosTablas.NINO_TABLE_NAME} WHERE ${DatosTablas.NINO_COL_ID} == '$_id'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {
                usuario.TN_id = cursor.getInt(cursor.getColumnIndex(DatosTablas.NINO_COL_ID))
                usuario.TN_Nombre = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_NOMBRE))
                usuario.TN_Apellido = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_APELLIDO))
                usuario.TN_Fecha_Nac = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_FECHA))
                usuario.TN_Imagen = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_IMAGEN))
                usuario.TN_Telefono = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_TELEFONO))
                usuario.TN_Direccion = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_DIRECCION))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario
    }

    fun BuscarNinoData(nombre:String,apellido:String, telefono:String,Direccion:String,fecha:String): Int {
        val usuario = UsuarioNino()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM ${DatosTablas.NINO_TABLE_NAME} WHERE ${DatosTablas.NINO_COL_NOMBRE} == '$nombre' AND ${DatosTablas.NINO_COL_APELLIDO} == '$apellido' AND ${DatosTablas.NINO_COL_TELEFONO} == '$telefono' AND ${DatosTablas.NINO_COL_DIRECCION} == '$Direccion' AND ${DatosTablas.NINO_COL_FECHA} == '$fecha'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.isAfterLast == false) {
                usuario.TN_id = cursor.getInt(cursor.getColumnIndex(DatosTablas.NINO_COL_ID))
                usuario.TN_Nombre = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_NOMBRE))
                usuario.TN_Apellido = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_APELLIDO))
                usuario.TN_Fecha_Nac = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_FECHA))
                usuario.TN_Imagen = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_IMAGEN))
                usuario.TN_Telefono = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_TELEFONO))
                usuario.TN_Direccion = cursor.getString(cursor.getColumnIndex(DatosTablas.NINO_COL_DIRECCION))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario.TN_id.toInt()
    }

    fun addNino(usuarioMaster: UsuarioNino){
        try {
            val db : SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(DatosTablas.NINO_COL_NOMBRE,  usuarioMaster.TN_Nombre)
            values.put(DatosTablas.NINO_COL_APELLIDO,  usuarioMaster.TN_Apellido)
            values.put(DatosTablas.NINO_COL_FECHA,  usuarioMaster.TN_Fecha_Nac)
            values.put(DatosTablas.NINO_COL_IMAGEN,  usuarioMaster.TN_Imagen)
            values.put(DatosTablas.NINO_COL_TELEFONO,  usuarioMaster.TN_Telefono)
            values.put(DatosTablas.NINO_COL_DIRECCION,  usuarioMaster.TN_Direccion)
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
        values.put(DatosTablas.NINO_COL_ID,  usuarioNino.TN_id)
        values.put(DatosTablas.NINO_COL_NOMBRE,  usuarioNino.TN_Nombre)
        values.put(DatosTablas.NINO_COL_APELLIDO,  usuarioNino.TN_Apellido)
        values.put(DatosTablas.NINO_COL_FECHA,  usuarioNino.TN_Fecha_Nac)
        values.put(DatosTablas.NINO_COL_IMAGEN,  usuarioNino.TN_Imagen)
        values.put(DatosTablas.NINO_COL_TELEFONO,  usuarioNino.TN_Telefono)
        values.put(DatosTablas.NINO_COL_DIRECCION,  usuarioNino.TN_Direccion)

        return db.update(DatosTablas.NINO_TABLE_NAME,values,"${DatosTablas.NINO_COL_ID} = ?", arrayOf(usuarioNino.TN_id.toString()))

    }

    fun deleteUser(usuarioNino: UsuarioNino) {
        val db:SQLiteDatabase = this.writableDatabase
        db.delete(DatosTablas.NINO_TABLE_NAME, "${DatosTablas.NINO_COL_ID}=?", arrayOf(usuarioNino.TN_id.toString()))
        db.close()
    }

    /**
     * METODOS CRUD DE LA TABLA VALORACIONES
     */

    fun ObtenerValroaciones(CodigoNino: Int):ArrayList<Tab_Observacion>{
        val lstValoracion = ArrayList<Tab_Observacion>()
        val selectquery  = "SELECT * FROM ${DatosTablas.VAL_TABLE_NAME} WHERE ${DatosTablas.VAL_COL_TN_FK} == $CodigoNino"
        val db : SQLiteDatabase = this.writableDatabase
        val cursor : Cursor = db.rawQuery(selectquery, null)
        if (cursor.moveToFirst())
        {
            do{
                val valoracion = Tab_Observacion()
                valoracion.TV_id = cursor.getInt(cursor.getColumnIndex(DatosTablas.VAL_COL_ID))
                valoracion.TV_TM_ID = cursor.getInt(cursor.getColumnIndex(DatosTablas.VAL_COL_TM_FK))
                valoracion.TV_TN_ID = cursor.getInt(cursor.getColumnIndex(DatosTablas.VAL_COL_TN_FK))
                valoracion.TV_Fecha = cursor.getString(cursor.getColumnIndex(DatosTablas.VAL_COL_FECHA))
                valoracion.TV_Observacion = cursor.getString(cursor.getColumnIndex(DatosTablas.VAL_COL_Observacion))
                lstValoracion.add(valoracion)
            }while (cursor.moveToNext())
        }
        db.close()
        return lstValoracion
    }

    val allObservacion: List<Tab_Observacion>
        get() {
            val lstValoracion = ArrayList<Tab_Observacion>()
            val selectquery  = "SELECT * FROM ${DatosTablas.VAL_TABLE_NAME}"
            val db : SQLiteDatabase = this.writableDatabase
            val cursor : Cursor = db.rawQuery(selectquery, null)
            if (cursor.moveToFirst())
            {
                do{
                    val valoracion = Tab_Observacion()
                    valoracion.TV_id = cursor.getInt(cursor.getColumnIndex(DatosTablas.VAL_COL_ID))
                    valoracion.TV_TM_ID = cursor.getInt(cursor.getColumnIndex(DatosTablas.VAL_COL_TM_FK))
                    valoracion.TV_TN_ID = cursor.getInt(cursor.getColumnIndex(DatosTablas.VAL_COL_TN_FK))
                    valoracion.TV_Fecha = cursor.getString(cursor.getColumnIndex(DatosTablas.VAL_COL_FECHA))
                    valoracion.TV_Observacion = cursor.getString(cursor.getColumnIndex(DatosTablas.VAL_COL_Observacion))
                    lstValoracion.add(valoracion)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstValoracion
        }

    fun RegistrarValoracion(observacion:Tab_Observacion){
        try {
            val db : SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(DatosTablas.VAL_COL_TM_FK,  observacion.TV_TM_ID)
            values.put(DatosTablas.VAL_COL_TN_FK,  observacion.TV_TN_ID)
            values.put(DatosTablas.VAL_COL_FECHA,  observacion.TV_Fecha)
            values.put(DatosTablas.VAL_COL_Observacion,  observacion.TV_Observacion)
            db.insert(DatosTablas.VAL_TABLE_NAME, null, values)
            TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            db.close()
        }catch (e: Exception){
            TastyToast.makeText(context, "Error Ingresando", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }
}