package com.example.pasantiaandrade

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.Model.UsuarioNino

class Listado_Ninos : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    var allNino: List<UsuarioNino>?=null
    var allObservacion: List<Tab_Observacion>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado__ninos)

        dbHelper = DBHelper(this)
        allNino = dbHelper.allNino
        allObservacion = dbHelper.allObservacion

        for (item in allNino!!){
            Log.d("Codigo del Nino","${item.TN_id} ${item.TN_Nombre}")
        }
        for (item in allObservacion!!){
            Log.d("Codigo de oBSERVACION","${item.TV_TN_ID} ${item.TV_TM_ID}")
        }
    }
}
