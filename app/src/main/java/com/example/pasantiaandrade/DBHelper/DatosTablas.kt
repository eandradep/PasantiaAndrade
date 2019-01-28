package com.example.pasantiaandrade.DBHelper

class DatosTablas {

    companion object {
        val DATABASE_VER = 1
        val DATABASE_NAME = "EDMTB.db"

        var NINO_TABLE_NAME = "TAB_NINO"
        var NINO_COL_ID = "TN_Id"
        var NINO_COL_NOMBRE = "TN_Nombre"
        var NINO_COL_APELLIDO = "TN_Apellido"
        var NINO_COL_FECHA = "TN_Fecha"
        var NINO_COL_IMAGEN = "TN_Imagen"
        var NINO_COL_TELEFONO= "TN_Telefono"
        var NINO_COL_DIRECCION = "TN_Direccion"

        var VAL_TABLE_NAME = "TAB_VALORACION"
        var VAL_COL_ID = "TV_Id"
        var VAL_COL_TM_FK = "TV_TM_Id"
        var VAL_COL_TN_FK = "TV_TN_Id"
        var VAL_COL_FECHA = "TV_Fecha"
        var VAL_COL_Observacion = "TV_Observacion"


        var USER_TABLE_NAME = "TAB_TERAPISTA_MASTER"
        var USER_COL_ID = "TM_ID"
        var USER_COL_NOMBRE = "TM_Nombre"
        var USER_COL_APELLIDO = "TM_Apellido"
        var USER_COL_TIPO = "TM_Tipo"
        var USER_COL_IMAGEN = "TM_Imagen"
        var USER_COL_TELEFONO= "TM_Telefono"
        var USER_COL_PASSWORD = "TM_Password"
    }
}
