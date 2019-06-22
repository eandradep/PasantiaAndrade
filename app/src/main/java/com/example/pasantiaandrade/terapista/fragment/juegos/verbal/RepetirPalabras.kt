package com.example.pasantiaandrade.terapista.fragment.juegos.verbal

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.ConectarBt
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.dbhelper.DBHelper
import kotlinx.android.synthetic.main.activity_repetir_palabras.*
import cn.pedant.SweetAlert.SweetAlertDialog
import com.sdsmdg.tastytoast.TastyToast




class RepetirPalabras : AppCompatActivity() , RecognitionListener {


    private var listaImagenes: IntArray? = null

    private var listaNombres: Array<String>?=null

    private var numerosAleatorios:IntArray?=null

    private val REQUEST_RECORD_PERMISSION = 100

    private var progressBar: ProgressBar? = null

    private var speech: SpeechRecognizer? = null

    private var recognizerIntent: Intent? = null

    private val LOG_TAG = "VoiceRecognitionActivity"

    private lateinit var conectarBt : ConectarBt

    private lateinit var mp : MediaPlayer

    var contador:Int=0

    private var intentos:Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repetir_palabras)

        cargarImagenesAleatorias()

        this.cargarEstilos()

        var dispo = DBHelper(this).listadoDispositivoBluetooth
        if (dispo.isNotEmpty()) {
            for (item in dispo)
                conectarBt = ConectarBt(this,  item.dispositivoDireccion.toString())
        } else
            this.finish()

        this.initEscucha()

        btnSalirLenguaje.setOnClickListener { finish() }
    }

    private fun comando(comando: String, Audio: Int?){
        conectarBt.sendCommand(comando)
        if (Audio !=null){
            this.mp = MediaPlayer.create(this, Audio)
            this.mp.start()
        }
    }

    private fun initEscucha() {
        progressBar = findViewById<View>(R.id.progressBar1) as ProgressBar

        progressBar!!.visibility = View.INVISIBLE
        speech = SpeechRecognizer.createSpeechRecognizer(this)
        // Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this))
        speech!!.setRecognitionListener(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "es")
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        recognizerIntent!!.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)

        imgbtnEscuchar.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action){
                MotionEvent.ACTION_DOWN ->{
                    progressBar!!.visibility = View.VISIBLE
                    progressBar!!.isIndeterminate = true
                    ActivityCompat.requestPermissions(this@RepetirPalabras,
                            arrayOf(Manifest.permission.RECORD_AUDIO),
                            REQUEST_RECORD_PERMISSION)
                }
                MotionEvent.ACTION_UP -> {
                    progressBar!!.isIndeterminate = false
                    progressBar!!.visibility = View.INVISIBLE
                    speech!!.stopListening()
                }
            }
            return@OnTouchListener true
        })
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

    private fun validarRespuesta(listaRespuestas: List<String>) {
        this.intentos += 1
        var imagenEncontrada: Boolean = false
        for (posibleRespuesta in listaRespuestas){
            lblcontadorVerbal?.text = intentos.toString()
            var posElemento = this!!.numerosAleatorios!![contador]
            if (posibleRespuesta.equals(this!!.listaNombres!![posElemento])){
                if (contador < 2){
                    contador += 1
                    cargarImagen()
                    imagenEncontrada = true
                    comando("b", R.raw.correcto)
                    TastyToast.makeText(getApplicationContext(), "Correcto !", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                    break
                }else {
                    comando("b", R.raw.correcto)
                    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Completaste EL Juego!")
                            .setConfirmClickListener { this.finish() }
                            .show()

                }
            }
        }
        if (!imagenEncontrada){
            comando("b", R.raw.mal)
            TastyToast.makeText(getApplicationContext(), "Intentalo Nuevamente !", TastyToast.LENGTH_SHORT, TastyToast.WARNING)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                speech!!.startListening(recognizerIntent)
            } else {
                Toast.makeText(this, "Permission Denied!", Toast
                        .LENGTH_SHORT).show()
            }
        }
    }

    public override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

    }

    @SuppressLint("LongLogTag")
    override fun onStop() {
        super.onStop()
        if (speech != null) {
            speech!!.destroy()
            Log.i(LOG_TAG, "destroy")
        }
    }

    @SuppressLint("LongLogTag")
    override fun onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech")
        progressBar!!.isIndeterminate = false
        progressBar!!.max = 10
    }

    @SuppressLint("LongLogTag")
    override fun onBufferReceived(buffer: ByteArray) {
        Log.i(LOG_TAG, "onBufferReceived: $buffer")
    }

    override fun onEndOfSpeech() {
        //Log.i(LOG_TAG, "onEndOfSpeech")
        progressBar!!.isIndeterminate = true
        //toggleButton!!.isChecked = false
    }

    override fun onError(errorCode: Int) {
        val errorMessage = getErrorText(errorCode)
        //Log.d(LOG_TAG, "FAILED $errorMessage")
        //returnedText!!.text = errorMessage
        //toggleButton!!.isChecked = false
    }

    override fun onEvent(arg0: Int, arg1: Bundle) {
        //Log.i(LOG_TAG, "onEvent")
    }

    override fun onPartialResults(arg0: Bundle) {
        //Log.i(LOG_TAG, "onPartialResults")
    }

    override fun onReadyForSpeech(arg0: Bundle) {
        //Log.i(LOG_TAG, "onReadyForSpeech")
    }

    override fun onResults(results: Bundle) {
        //Log.i(LOG_TAG, "onResults")
        val matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var text = ""
        for (result in matches!!)
            text += result + "-"

        val cadena = text.split("-")
        this.validarRespuesta(cadena)
        lblGrabar!!.setText(text)
    }

    override fun onRmsChanged(rmsdB: Float) {
        //Log.i(LOG_TAG, "onRmsChanged: $rmsdB")
        progressBar!!.setProgress(rmsdB.toInt())
    }

    fun getErrorText(errorCode: Int): String {
        val message: String
        when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> message = "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> message = "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> message = "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> message = "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> message = "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> message = "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> message = "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> message = "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> message = "No speech input"
            else -> message = "Didn't understand, please try again."
        }
        return message
    }
}
