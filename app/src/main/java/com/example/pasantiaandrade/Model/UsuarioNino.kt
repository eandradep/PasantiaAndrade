package com.example.pasantiaandrade.Model

import java.io.Serializable

class UsuarioNino : Serializable  {

    var TN_id : Int=0
    var TN_Nombre : String?=null
    var TN_Apellido:String?=null
    var TN_Fecha_Nac:String?=null
    var TN_Imagen:String?=null
    var TN_Telefono:String?=null
    var TN_Direccion:String?=null

    constructor(){}

    constructor(id: Int , nombre: String, apellido:String, fecha_Nacimiento:String, imagen:String, telefono:String, direccion:String){
        this.TN_id = id
        this.TN_Nombre = nombre
        this.TN_Apellido = apellido
        this.TN_Fecha_Nac= fecha_Nacimiento
        this.TN_Imagen= imagen
        this.TN_Telefono =telefono
        this.TN_Direccion = direccion
    }

}