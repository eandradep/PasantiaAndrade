package com.example.pasantiaandrade.terapista.fragment.juegos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.adaptador.sliders.SlideListaJuegos
import com.example.pasantiaandrade.modelos.Nino
import com.example.pasantiaandrade.modelos.Terapista

class SeleccionJuegos : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_juegos)

        val people : Terapista? = intent.extras.getSerializable("Terapista") as? Terapista
        val nino:Nino?= intent.extras.getSerializable("Infante") as? Nino


        viewPager = findViewById(R.id.contenedorListaJuegos)
        viewPager.adapter = SlideListaJuegos(this, people!!, nino!!)
    }
}
