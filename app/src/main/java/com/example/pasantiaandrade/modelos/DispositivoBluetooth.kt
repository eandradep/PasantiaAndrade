package com.example.pasantiaandrade.modelos

class DispositivoBluetooth {

    var dispositivoDireccion: String? = null
    var dispositivoNombre:String? =null
    var dispositivoPredefinido:String? = null


    constructor()

    constructor(dispositivoDireccion: String?, dispositivoNombre: String?, dispositivoPredefinido: String?) {
        this.dispositivoDireccion = dispositivoDireccion
        this.dispositivoNombre = dispositivoNombre
        this.dispositivoPredefinido = dispositivoPredefinido
    }
}