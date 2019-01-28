package com.example.pasantiaandrade.Model

class Tab_Observacion {
    var TV_id : Int=0
    var TV_TM_ID : Int=0
    var TV_TN_ID : Int=0
    var TV_Fecha : String?=null
    var TV_Observacion : String?=null

    constructor()

    constructor(TV_id: Int, TV_TM_ID: Int, TV_TN_ID: Int, TV_Fecha: String?, TV_Observacion: String?) {
        this.TV_id = TV_id
        this.TV_TM_ID = TV_TM_ID
        this.TV_TN_ID = TV_TN_ID
        this.TV_Fecha = TV_Fecha
        this.TV_Observacion = TV_Observacion
    }
}