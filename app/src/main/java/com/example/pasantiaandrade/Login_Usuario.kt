package com.example.pasantiaandrade

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioMT
import com.example.pasantiaandrade.Adaptador.Sliders.slide_tipos_registro
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.Model.UsuarioNino

class Login_Usuario : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    private lateinit var db:DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_usuarios)

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        db = DBHelper(this)
        if (db.getTask("patito123","master").TM_Nombre.isNullOrEmpty()) CrearUsuariosPrueba(db)
        viewPager = findViewById(R.id.viewPageTipoRegistro)
        viewPager.adapter = slide_tipos_registro(this)


    }

    private fun CrearUsuariosPrueba(db: DBHelper) {
        db.addTerapistaMaster(UsuarioMT(0,"Edison Bladimir","Andrade Prieto","master","none","0998275721","patito123"))

        db.addNino(UsuarioNino(0,"Andres","Cardenas","14/08/1994","none","0998275721","Ricaurte"))
        db.addObservacion(Tab_Observacion(0,1,1,"01/02/19","Registro Inicial"))
        db.addObservacion(Tab_Observacion(0,1,1,"02/02/19","Atitud Negativa, No hace caso"))
        db.addObservacion(Tab_Observacion(0,1,1,"03/02/19","Mas colaborativo"))
        db.addObservacion(Tab_Observacion(0,1,1,"04/02/19","Usa las Herramientas"))
        db.addObservacion(Tab_Observacion(0,1,1,"05/02/19","Primera Evaluacion"))

        db.addNino(UsuarioNino(0,"Rodrigo","Ramires","14/03/1994","none","0998475721","Arenal"))
        db.addObservacion(Tab_Observacion(0,1,2,"01/02/19","Registro Inicial"))
        db.addObservacion(Tab_Observacion(0,1,2,"02/02/19","Atitud Negativa, No hace caso"))
        db.addObservacion(Tab_Observacion(0,1,2,"03/02/19","Mas colaborativo"))
        db.addObservacion(Tab_Observacion(0,1,2,"04/02/19","Usa las Herramientas"))
        db.addObservacion(Tab_Observacion(0,1,2,"05/02/19","Primera Evaluacion"))

        db.addNino(UsuarioNino(0,"Carla","Masias","10/01/1994","none","0998275721","Ricaurte"))
        db.addObservacion(Tab_Observacion(0,1,3,"01/02/19","Registro Inicial"))
        db.addObservacion(Tab_Observacion(0,1,3,"02/02/19","Atitud Negativa, No hace caso"))
        db.addObservacion(Tab_Observacion(0,1,3,"03/02/19","Mas colaborativo"))
        db.addObservacion(Tab_Observacion(0,1,3,"04/02/19","Usa las Herramientas"))
        db.addObservacion(Tab_Observacion(0,1,3,"05/02/19","Primera Evaluacion"))
    }

}
