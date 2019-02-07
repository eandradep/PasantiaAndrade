package com.example.pasantiaandrade.master.nino

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.adaptador.Sliders.SlideListadoNinos
import com.example.pasantiaandrade.R

class Listado_Ninos : AppCompatActivity() {


    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado__ninos)

        viewPager = findViewById(R.id.viewPageListadoNino)
        viewPager.adapter = SlideListadoNinos(this, DBHelper(this).listadoNinos)

    }
}
