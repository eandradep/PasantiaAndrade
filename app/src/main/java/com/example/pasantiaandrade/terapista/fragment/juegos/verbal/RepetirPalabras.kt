package com.example.pasantiaandrade.terapista.fragment.juegos.verbal

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.R
import kotlinx.android.synthetic.main.activity_repetir_palabras.*

import kotlin.Int.Companion as Int1


class RepetirPalabras : AppCompatActivity() {

    private val RECOGNIZE_SPEECH_ACTIVITY = 1
    var listaImagenes: IntArray? = null
    var listaNombres: Array<String>?=null
    var numerosAleatorios:IntArray?=null
    var contador:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repetir_palabras)
        imgbtnEscuchar.setOnClickListener { this.iniciarEscucha() }
        cargarImagenesAleatorias()
        this.cargarEstilos()
    }

    private fun cargarImagenesAleatorias(){
        listaImagenes = intArrayOf(R.drawable.pareja_1, R.drawable.pareja_2,R.drawable.pareja_3)
        listaNombres = arrayOf("corriente","agua","fuego")
        numerosAleatorios = intArrayOf(0,1,2)
        numerosAleatorios = MetodosAyuda(this).desordenarNumeros(numerosAleatorios!!)
        this.cargarImagen()
    }

    private fun cargarImagen(){
        var numero = this!!.numerosAleatorios!![contador]
        Glide.with(this).load(this!!.listaImagenes!![numero]).into(imagenPrueba)
    }

    private fun cargarEstilos() {
        val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        lblGrabar.typeface=typeface
        lblcontadorVerbal.typeface=typeface

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RECOGNIZE_SPEECH_ACTIVITY ->
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val strSpeech2Text = speech[0]
                    lblGrabar?.text = strSpeech2Text
                    this.validarRespuesta(strSpeech2Text)
                }
            else -> {
            }
        }
    }

    private var intentos:Int =0

    private fun validarRespuesta(strSpeech2Text: String) {
        this.intentos += 1
        lblcontadorVerbal?.text = intentos.toString()
        var numero = this!!.numerosAleatorios!![contador]
        if (strSpeech2Text.equals(this!!.listaNombres!![numero])){
            if (contador < 2){
                contador += 1
                cargarImagen()
            }else
                Toast.makeText(this,"Juego Terminado",Toast.LENGTH_SHORT).show()
        }else
            Toast.makeText(this,"Intenta Nuevamente",Toast.LENGTH_SHORT).show()
    }

    fun iniciarEscucha() {
        val intentActionRecognizeSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX")
        try {
            startActivityForResult(intentActionRecognizeSpeech,RECOGNIZE_SPEECH_ACTIVITY)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show()
        }
    }

}
