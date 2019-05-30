package com.example.pasantiaandrade.terapista.fragment.juegos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.adaptador.sliders.SlideListaJuegos
import com.example.pasantiaandrade.adaptador.sliders.SlideTipoLogin

class SeleccionJuegos : AppCompatActivity() {

    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_juegos)

        viewPager = findViewById(R.id.contenedorListaJuegos)
        viewPager.adapter = SlideListaJuegos(this)
    }
}
