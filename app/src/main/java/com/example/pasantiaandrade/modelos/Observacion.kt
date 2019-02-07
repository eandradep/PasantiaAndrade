package com.example.pasantiaandrade.modelos

class Observacion {
    var id : Int=0
    var terapistaid : Int=0
    var ninoid : Int=0
    var fecha : String?=null
    var observacion : String?=null

    constructor()

    constructor(TV_id: Int, TV_TM_ID: Int, TV_TN_ID: Int, TV_Fecha: String?, TV_Observacion: String?) {
        this.id = TV_id
        this.terapistaid = TV_TM_ID
        this.ninoid = TV_TN_ID
        this.fecha = TV_Fecha
        this.observacion = TV_Observacion
    }
}