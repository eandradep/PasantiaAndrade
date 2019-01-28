package com.example.pasantiaandrade.Model

import java.io.Serializable

class UsuarioMT : Serializable{

    var id : Int=0
    var nombre : String?=null
    var apellido:String?=null
    var tipo:String?=null
    var imagen:String?=null
    var telefono:String?=null
    var password:String?=null

    constructor(){}

    constructor(id:Int, nombre:String, apellido:String, tipo:String, imagen:String, telefono:String, password:String){
        this.id = id
        this.nombre = nombre
        this.apellido = apellido
        this.tipo = tipo
        this.imagen = imagen
        this.telefono = telefono
        this.password = password
    }

}