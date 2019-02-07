package com.example.pasantiaandrade.modelos

import java.io.Serializable

class Nino : Serializable  {

    var id : Int=0
    var nombre : String?=null
    var apellido:String?=null
    var fechanacimiento:String?=null
    var imagen:String?=null
    var telefono:String?=null
    var direccion:String?=null

    constructor()

    constructor(id: Int , nombre: String, apellido:String, fecha_Nacimiento:String, imagen:String, telefono:String, direccion:String){
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.fechanacimiento= fecha_Nacimiento
        this.imagen= imagen
        this.telefono =telefono
        this.direccion = direccion
    }

}