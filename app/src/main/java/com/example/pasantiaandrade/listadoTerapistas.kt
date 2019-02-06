package com.example.pasantiaandrade

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.pasantiaandrade.Adaptador.Listas.ListadoTerapistas1
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioMT
import kotlinx.android.synthetic.main.activity_listado_terapistas.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class listadoTerapistas : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_terapistas)

        listViewTerapistas.adapter = ListadoTerapistas1(this,DBHelper(this).allusers)

    }

    fun mostrar(position: Int){
        Log.d("Posicion","$position")
    }
}

