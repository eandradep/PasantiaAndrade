package com.example.pasantiaandrade

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioMT
import com.example.pasantiaandrade.Adaptadores.Slider.slide_tipos_registro

class seleccion_usuarios : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    private lateinit var db:DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_usuarios)

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        db = DBHelper(this)
        if (db.getTask("patito123","master").TM_Nombre.isNullOrEmpty()) db.addUser(UsuarioMT(0,"Edison Bladimir","Andrade Prieto","master","none","0998275721","patito123"))
        viewPager = findViewById(R.id.viewPageTipoRegistro)
        viewPager.adapter = slide_tipos_registro(this)
    }

}
