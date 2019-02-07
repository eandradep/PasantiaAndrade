package com.example.pasantiaandrade.dbhelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_APELLIDO
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_ID
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_IMAGEN
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_NOMBRE
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_PASSWORD
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_TELEFONO
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_COL_TIPO
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.DATABASE_NAME
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.DATABASE_VER
import com.example.pasantiaandrade.dbhelper.VariablesGlobales.Companion.USER_TABLE_NAME
import com.example.pasantiaandrade.modelos.Observacion
import com.example.pasantiaandrade.modelos.Nino
import com.sdsmdg.tastytoast.TastyToast
import java.lang.Exception


@SuppressLint("ByteOrderMark")
class DBHelper (private var context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VER) {



    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL("CREATE TABLE $USER_TABLE_NAME($USER_COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$USER_COL_NOMBRE TEXT,$USER_COL_APELLIDO TEXT, $USER_COL_TIPO TEXT,$USER_COL_IMAGEN TEXT, $USER_COL_TELEFONO TEXT, $USER_COL_PASSWORD TEXT)")
            db.execSQL("CREATE TABLE ${VariablesGlobales.NINO_TABLE_NAME}(${VariablesGlobales.NINO_COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT,${VariablesGlobales.NINO_COL_NOMBRE} TEXT, ${VariablesGlobales.NINO_COL_APELLIDO} TEXT, ${VariablesGlobales.NINO_COL_FECHA} TEXT,${VariablesGlobales.NINO_COL_IMAGEN} TEXT, ${VariablesGlobales.NINO_COL_TELEFONO} TEXT, ${VariablesGlobales.NINO_COL_DIRECCION} TEXT)")
            db.execSQL("CREATE TABLE ${VariablesGlobales.VAL_TABLE_NAME}(${VariablesGlobales.VAL_COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${VariablesGlobales.VAL_COL_TM_FK} INTEGER,${VariablesGlobales.VAL_COL_TN_FK} INTEGER, ${VariablesGlobales.VAL_COL_FECHA} TEXT, ${VariablesGlobales.VAL_COL_Observacion} TEXT, FOREIGN KEY(${VariablesGlobales.VAL_COL_TM_FK}) REFERENCES $USER_TABLE_NAME($USER_COL_ID),FOREIGN KEY(${VariablesGlobales.VAL_COL_TN_FK}) REFERENCES ${VariablesGlobales.NINO_TABLE_NAME}(${VariablesGlobales.NINO_COL_ID}))")
            TastyToast.makeText(context, "Creacion de Tablas Correcta", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
        }catch (e: Exception){
            TastyToast.makeText(context, "Creacion de Tablas Incorrecta", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS ${VariablesGlobales.NINO_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${VariablesGlobales.VAL_TABLE_NAME}")
        onCreate(db)
    }

    /**
    * METODOS CRUD DE LA TABLA DE USUARIO
    * OBTENER TODOS LOS USUARIOS
    * OBTENER UN SOLO USUARIO
    * AGREGAR USUARIO
    * ACTUALIZAR USUARIO
    * ELIMINAR USUARIO*/

    val allusers: List<Terapista>
        @SuppressLint("Recycle")
        get() {
            Log.d("Cosultando","Datos")
            val lstUsuers = ArrayList<Terapista>()
            val selectQuery  = "SELECT * FROM $USER_TABLE_NAME"
            val db : SQLiteDatabase = this.writableDatabase
            val cursor : Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst())
            {
                do{
                    val usuario = Terapista()
                    usuario.id = cursor.getInt(cursor.getColumnIndex(USER_COL_ID))
                    usuario.nombre = cursor.getString(cursor.getColumnIndex(USER_COL_NOMBRE))
                    usuario.apellido = cursor.getString(cursor.getColumnIndex(USER_COL_APELLIDO))
                    usuario.tipo = cursor.getString(cursor.getColumnIndex(USER_COL_TIPO))
                    usuario.imagen = cursor.getString(cursor.getColumnIndex(USER_COL_IMAGEN))
                    usuario.telefono = cursor.getString(cursor.getColumnIndex(USER_COL_TELEFONO))
                    usuario.password = cursor.getString(cursor.getColumnIndex(USER_COL_PASSWORD))
                    lstUsuers.add(usuario)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstUsuers

        }

    fun getTask(_id: String, tipo:String): Terapista {
        val usuario = Terapista()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USER_TABLE_NAME WHERE $USER_COL_PASSWORD == '$_id' AND $USER_COL_TIPO == '$tipo'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                usuario.id = cursor.getInt(cursor.getColumnIndex(USER_COL_ID))
                usuario.nombre = cursor.getString(cursor.getColumnIndex(USER_COL_NOMBRE))
                usuario.apellido = cursor.getString(cursor.getColumnIndex(USER_COL_APELLIDO))
                usuario.tipo = cursor.getString(cursor.getColumnIndex(USER_COL_TIPO))
                usuario.imagen = cursor.getString(cursor.getColumnIndex(USER_COL_IMAGEN))
                usuario.telefono = cursor.getString(cursor.getColumnIndex(USER_COL_TELEFONO))
                usuario.password = cursor.getString(cursor.getColumnIndex(USER_COL_PASSWORD))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario
    }

    fun obtenerImagenMaster(_id: Int): String {
        val usuario = Terapista()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $USER_TABLE_NAME WHERE $USER_COL_ID == '$_id'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {

                usuario.imagen = cursor.getString(cursor.getColumnIndex(USER_COL_IMAGEN))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario.imagen.toString()
    }


    fun addTerapistaMaster(terapista:Terapista){
        val db : SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(USER_COL_NOMBRE,  terapista.nombre)
        values.put(USER_COL_APELLIDO,  terapista.apellido)
        values.put(USER_COL_TIPO,  terapista.tipo)
        values.put(USER_COL_IMAGEN,  terapista.imagen)
        values.put(USER_COL_TELEFONO,  terapista.telefono)
        values.put(USER_COL_PASSWORD,  terapista.password)
        db.insert(USER_TABLE_NAME, null, values)
        TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
        db.close()
    }

    fun updateUser(terapista:Terapista):Int {
        val db:SQLiteDatabase = this.writableDatabase
        val values = ContentValues()
        values.put(USER_COL_ID,  terapista.id)
        values.put(USER_COL_NOMBRE,  terapista.nombre)
        values.put(USER_COL_APELLIDO,  terapista.apellido)
        values.put(USER_COL_TIPO,  terapista.tipo)
        values.put(USER_COL_IMAGEN,  terapista.imagen)
        values.put(USER_COL_TELEFONO,  terapista.telefono)
        values.put(USER_COL_PASSWORD,  terapista.password)
        TastyToast.makeText(context, "Actualizacion Exitosa", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
        return db.update(USER_TABLE_NAME,values,"$USER_COL_ID = ?", arrayOf(terapista.id.toString()))

    }

/**
 *     fun deleteUser(usuarioMT:Terapista) {
val db:SQLiteDatabase = this.writableDatabase
db.delete(USER_TABLE_NAME, "$USER_COL_ID=?", arrayOf(usuarioMT.id.toString()))
db.close()
}


fun deleteUser(usuarioNino: Nino) {
val db:SQLiteDatabase = this.writableDatabase
db.delete(VariablesGlobales.NINO_TABLE_NAME, "${VariablesGlobales.NINO_COL_ID}=?", arrayOf(usuarioNino.id.toString()))
db.close()
}

fun buscarNinoID(_id: Int): Nino {
val usuario = Nino()
val db = writableDatabase
val selectQuery = "SELECT  * FROM ${VariablesGlobales.NINO_TABLE_NAME} WHERE ${VariablesGlobales.NINO_COL_ID} == '$_id'"
val cursor = db.rawQuery(selectQuery, null)
if (cursor != null) {
cursor.moveToFirst()
while (!cursor.isAfterLast) {
usuario.id = cursor.getInt(cursor.getColumnIndex(VariablesGlobales.NINO_COL_ID))
usuario.nombre = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_NOMBRE))
usuario.apellido = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_APELLIDO))
usuario.fechanacimiento = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_FECHA))
usuario.imagen = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_IMAGEN))
usuario.telefono = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_TELEFONO))
usuario.direccion = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_DIRECCION))
cursor.moveToNext()
}
}
cursor.close()
return usuario
}

fun updateNino(usuarioNino: Nino):Int {
val db:SQLiteDatabase = this.writableDatabase
val values = ContentValues()
values.put(VariablesGlobales.NINO_COL_ID,  usuarioNino.id)
values.put(VariablesGlobales.NINO_COL_NOMBRE,  usuarioNino.nombre)
values.put(VariablesGlobales.NINO_COL_APELLIDO,  usuarioNino.apellido)
values.put(VariablesGlobales.NINO_COL_FECHA,  usuarioNino.fechanacimiento)
values.put(VariablesGlobales.NINO_COL_IMAGEN,  usuarioNino.imagen)
values.put(VariablesGlobales.NINO_COL_TELEFONO,  usuarioNino.telefono)
values.put(VariablesGlobales.NINO_COL_DIRECCION,  usuarioNino.direccion)

return db.update(VariablesGlobales.NINO_TABLE_NAME,values,"${VariablesGlobales.NINO_COL_ID} = ?", arrayOf(usuarioNino.id.toString()))

}


 * */



    /**
    * METODOS CRUD DE LA TABLA DE USUARIO
    * OBTENER TODOS LOS USUARIOS
    * OBTENER UN SOLO USUARIO
    * AGREGAR USUARIO
    * ACTUALIZAR USUARIO
    * ELIMINAR USUARIO*/

    val listadoNinos: List<Nino>
        @SuppressLint("Recycle")
        get() {
            val lstNinos = ArrayList<Nino>()
            val selectQuery  = "SELECT * FROM ${VariablesGlobales.NINO_TABLE_NAME}"
            val db : SQLiteDatabase = this.writableDatabase
            val cursor : Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst())
            {
                do{
                    val nino = Nino()
                    nino.id = cursor.getInt(cursor.getColumnIndex(VariablesGlobales.NINO_COL_ID))
                    nino.nombre = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_NOMBRE))
                    nino.apellido = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_APELLIDO))
                    nino.fechanacimiento = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_FECHA))
                    nino.imagen = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_IMAGEN))
                    nino.telefono = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_TELEFONO))
                    nino.direccion = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_DIRECCION))
                    lstNinos.add(nino)
                }while (cursor.moveToNext())
            }
            db.close()
            return lstNinos

        }



    fun buscarNinoData(nombre:String, apellido:String, telefono:String, Direccion:String, fecha:String): Int {
        val usuario = Nino()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM ${VariablesGlobales.NINO_TABLE_NAME} WHERE ${VariablesGlobales.NINO_COL_NOMBRE} == '$nombre' AND ${VariablesGlobales.NINO_COL_APELLIDO} == '$apellido' AND ${VariablesGlobales.NINO_COL_TELEFONO} == '$telefono' AND ${VariablesGlobales.NINO_COL_DIRECCION} == '$Direccion' AND ${VariablesGlobales.NINO_COL_FECHA} == '$fecha'"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                usuario.id = cursor.getInt(cursor.getColumnIndex(VariablesGlobales.NINO_COL_ID))
                usuario.nombre = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_NOMBRE))
                usuario.apellido = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_APELLIDO))
                usuario.fechanacimiento = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_FECHA))
                usuario.imagen = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_IMAGEN))
                usuario.telefono = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_TELEFONO))
                usuario.direccion = cursor.getString(cursor.getColumnIndex(VariablesGlobales.NINO_COL_DIRECCION))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return usuario.id
    }

    fun addNino(master: Nino){
        try {
            val db : SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(VariablesGlobales.NINO_COL_NOMBRE,  master.nombre)
            values.put(VariablesGlobales.NINO_COL_APELLIDO,  master.apellido)
            values.put(VariablesGlobales.NINO_COL_FECHA,  master.fechanacimiento)
            values.put(VariablesGlobales.NINO_COL_IMAGEN,  master.imagen)
            values.put(VariablesGlobales.NINO_COL_TELEFONO,  master.telefono)
            values.put(VariablesGlobales.NINO_COL_DIRECCION,  master.direccion)
            db.insert(VariablesGlobales.NINO_TABLE_NAME, null, values)
            TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            db.close()
        }catch (e: Exception){
            TastyToast.makeText(context, "Error Ingresando", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }


    /**
     * METODOS CRUD DE LA TABLA VALORACIONES
     */

    @SuppressLint("Recycle")
    fun listadoOberservacion(codigoNino: Int):ArrayList<Observacion>{
        val lstValoracion = ArrayList<Observacion>()
        val selectQuery  = "SELECT * FROM ${VariablesGlobales.VAL_TABLE_NAME} WHERE ${VariablesGlobales.VAL_COL_TN_FK} == $codigoNino"
        val db : SQLiteDatabase = this.writableDatabase
        val cursor : Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst())
        {
            do{
                val valoracion = Observacion()
                valoracion.id = cursor.getInt(cursor.getColumnIndex(VariablesGlobales.VAL_COL_ID))
                valoracion.terapistaid = cursor.getInt(cursor.getColumnIndex(VariablesGlobales.VAL_COL_TM_FK))
                valoracion.ninoid = cursor.getInt(cursor.getColumnIndex(VariablesGlobales.VAL_COL_TN_FK))
                valoracion.fecha = cursor.getString(cursor.getColumnIndex(VariablesGlobales.VAL_COL_FECHA))
                valoracion.observacion = cursor.getString(cursor.getColumnIndex(VariablesGlobales.VAL_COL_Observacion))
                lstValoracion.add(valoracion)
            }while (cursor.moveToNext())
        }
        db.close()
        return lstValoracion
    }

    fun addObservacion(observacion:Observacion){
        try {
            val db : SQLiteDatabase = this.writableDatabase
            val values = ContentValues()
            values.put(VariablesGlobales.VAL_COL_TM_FK,  observacion.terapistaid)
            values.put(VariablesGlobales.VAL_COL_TN_FK,  observacion.ninoid)
            values.put(VariablesGlobales.VAL_COL_FECHA,  observacion.fecha)
            values.put(VariablesGlobales.VAL_COL_Observacion,  observacion.observacion)
            db.insert(VariablesGlobales.VAL_TABLE_NAME, null, values)
            TastyToast.makeText(context, "Ingreso Exitoso", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            db.close()
        }catch (e: Exception){
            TastyToast.makeText(context, "Error Ingresando", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }
}