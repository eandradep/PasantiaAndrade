package com.example.pasantiaandrade

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.adaptador.sliders.SlideTipoLogin
import com.example.pasantiaandrade.modelos.Observacion
import com.example.pasantiaandrade.modelos.Nino

class LoginUsuario : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    private lateinit var db:DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_usuarios)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        db = DBHelper(this)
        if (db.getTask("patito123","Master").nombre.isNullOrEmpty()) crearUsuarios(db)
        viewPager = findViewById(R.id.viewPageTipoRegistro)
        viewPager.adapter = SlideTipoLogin(this)


    }

    private fun crearUsuarios(db: DBHelper) {
        db.addTerapistaMaster(Terapista(0,"Edison Bladimir","Andrade Prieto","Master","none","0998275721","patito123"))
        db.addTerapistaMaster(Terapista(0,"Edison","Andrade ","Terapista","none","0998275721","abcde"))
        db.addTerapistaMaster(Terapista(0,"Bladimir","Andrade","Terapista","none","0998275721","12345"))
        db.addTerapistaMaster(Terapista(0,"Bladimir","Andrade Prieto","Terapista","none","0998275721","abcd1234"))

        db.addNino(Nino(0,"Andres","Cardenas","14/08/1994","none","0998275721","Ricaurte"))
        db.addObservacion(Observacion(0,1,1,"01/02/19","Registro Inicial"))
        db.addObservacion(Observacion(0,1,1,"02/02/19","Atitud Negativa, No hace caso"))
        db.addObservacion(Observacion(0,1,1,"03/02/19","Mas colaborativo"))
        db.addObservacion(Observacion(0,1,1,"04/02/19","Usa las Herramientas"))
        db.addObservacion(Observacion(0,1,1,"05/02/19","Primera Evaluacion"))

        db.addNino(Nino(0,"Rodrigo","Ramires","14/03/1994","none","0998475721","Arenal"))
        db.addObservacion(Observacion(0,1,2,"01/02/19","Registro Inicial"))
        db.addObservacion(Observacion(0,1,2,"02/02/19","Atitud Negativa, No hace caso"))
        db.addObservacion(Observacion(0,1,2,"03/02/19","Mas colaborativo"))
        db.addObservacion(Observacion(0,1,2,"04/02/19","Usa las Herramientas"))
        db.addObservacion(Observacion(0,1,2,"05/02/19","Primera Evaluacion"))

        db.addNino(Nino(0,"Carla","Masias","10/01/1994","none","0998275721","Ricaurte"))
        db.addObservacion(Observacion(0,1,3,"01/02/19","Registro Inicial"))
        db.addObservacion(Observacion(0,1,3,"02/02/19","Atitud Negativa, No hace caso"))
        db.addObservacion(Observacion(0,1,3,"03/02/19","Mas colaborativo"))
        db.addObservacion(Observacion(0,1,3,"04/02/19","Usa las Herramientas"))
        db.addObservacion(Observacion(0,1,3,"05/02/19","Primera Evaluacion"))
    }

}
