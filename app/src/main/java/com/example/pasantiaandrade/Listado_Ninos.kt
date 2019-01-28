package com.example.pasantiaandrade

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioNino

class Listado_Ninos : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    var allNino: List<UsuarioNino>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado__ninos)

        dbHelper = DBHelper(this)
        allNino = dbHelper.allNino

        for (item in allNino!!){
            Log.d("Codigo del Nino","${item.id} ${item.nombre}")
        }
    }
}
