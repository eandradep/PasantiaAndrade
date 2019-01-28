package com.example.pasantiaandrade.Model

import java.io.Serializable

class UsuarioNino : Serializable  {

    var id : Int=0
    var nombre : String?=null
    var apellido:String?=null
    var fecha_nacimiento:String?=null
    var imagen:String?=null
    var telefono:String?=null
    var direccion:String?=null

    constructor(){}

    constructor(id: Int , nombre: String, apellido:String, fecha_Nacimiento:String, imagen:String, telefono:String, direccion:String){
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.fecha_nacimiento= fecha_Nacimiento
        this.imagen= imagen
        this.telefono =telefono
        this.direccion = direccion
    }

}