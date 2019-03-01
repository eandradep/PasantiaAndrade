package com.example.pasantiaandrade.master.nino

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.adaptador.sliders.SlideListadoNinos
import com.example.pasantiaandrade.R

class ListadoNinos : AppCompatActivity() {


    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado__ninos)

        viewPager = findViewById(R.id.viewPageListadoNino)
        viewPager.adapter = SlideListadoNinos(this, DBHelper(this).listadoNinos,true,null)

    }
}
