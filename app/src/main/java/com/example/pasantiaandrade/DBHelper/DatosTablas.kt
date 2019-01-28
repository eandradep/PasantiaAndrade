package com.example.pasantiaandrade.DBHelper

class DatosTablas {

    companion object {
        val DATABASE_VER = 1
        val DATABASE_NAME = "EDMTB.db"

        var NINO_TABLE_NAME = "UsuarioNINO"
        var NINO_COL_ID = "Id"
        var NINO_COL_NOMBRE = "Nombre"
        var NINO_COL_APELLIDO = "Apellido"
        var NINO_COL_FECHA = "Fecha"
        var NINO_COL_IMAGEN = "Imagen"
        var NINO_COL_TELEFONO= "Telefono"
        var NINO_COL_DIRECCION = "Direccion"


        var USER_TABLE_NAME = "UsuarioMT"
        var COL_ID = "Id"
        var COL_NOMBRE = "Nombre"
        var COL_APELLIDO = "Apellido"
        var COL_TIPO = "Tipo"
        var COL_IMAGEN = "Imagen"
        var COL_TELEFONO= "Telefono"
        var COL_PASSWORD = "Password"
    }
}
