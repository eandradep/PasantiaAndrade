package com.example.pasantiaandrade.Model

import java.io.Serializable

class UsuarioMT : Serializable{

    var TM_Id : Int=0
    var TM_Nombre : String?=null
    var TM_Apellido:String?=null
    var TM_Tipo:String?=null
    var TM_Imagen:String?=null
    var TM_Telefono:String?=null
    var TM_Password:String?=null

    constructor(){}

    constructor(id:Int, nombre:String, apellido:String, tipo:String, imagen:String, telefono:String, password:String){
        this.TM_Id = id
        this.TM_Nombre = nombre
        this.TM_Apellido = apellido
        this.TM_Tipo = tipo
        this.TM_Imagen = imagen
        this.TM_Telefono = telefono
        this.TM_Password = password
    }

}