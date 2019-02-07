package com.example.pasantiaandrade.dbhelper

class VariablesGlobales {

    companion object {
        const val DATABASE_VER = 1
        const val DATABASE_NAME = "EDMTB.db"

        var NINO_TABLE_NAME = "TAB_NINO"
        var NINO_COL_ID = "TN_Id"
        var NINO_COL_NOMBRE = "nombre"
        var NINO_COL_APELLIDO = "apellido"
        var NINO_COL_FECHA = "TN_Fecha"
        var NINO_COL_IMAGEN = "imagen"
        var NINO_COL_TELEFONO= "telefono"
        var NINO_COL_DIRECCION = "direccion"

        var VAL_TABLE_NAME = "TAB_VALORACION"
        var VAL_COL_ID = "TV_Id"
        var VAL_COL_TM_FK = "TV_TM_Id"
        var VAL_COL_TN_FK = "TV_TN_Id"
        var VAL_COL_FECHA = "fecha"
        var VAL_COL_Observacion = "observacion"


        var USER_TABLE_NAME = "TAB_TERAPISTA_MASTER"
        var USER_COL_ID = "TM_ID"
        var USER_COL_NOMBRE = "nombre"
        var USER_COL_APELLIDO = "apellido"
        var USER_COL_TIPO = "tipo"
        var USER_COL_IMAGEN = "imagen"
        var USER_COL_TELEFONO= "telefono"
        var USER_COL_PASSWORD = "password"
    }
}
