package com.example.pasantiaandrade.terapista.fragment.juegos.control


import android.graphics.Typeface
import android.media.MediaPlayer
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.util.AndroidRuntimeException
import android.util.Log
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.ConectarBt
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.modelos.Nino
import com.example.pasantiaandrade.modelos.Terapista
import kotlinx.android.synthetic.main.activity_control_robot.*
import java.lang.NullPointerException
import java.util.*


class controlRobot : AppCompatActivity() {

    var people : Terapista? = null
    var nino: Nino? = null

    private lateinit var conectarBt :ConectarBt
    private lateinit var mp : MediaPlayer

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_robot)

        this.cargarImagenes()

        var dispo = DBHelper(this).listadoDispositivoBluetooth
        if (dispo.isNotEmpty()) {
            for (item in dispo)
                conectarBt = ConectarBt(this,  item.dispositivoDireccion.toString())
        } else
            this.finish()

        imgbtnCorreco.setOnClickListener { comando("b", R.raw.correcto) }

        imgbtninCorreco.setOnClickListener { comando("b", R.raw.mal) }

        btnMoverCabeza.setOnClickListener { comando("c", null) }

        imgbtnManoIzquierda.setOnClickListener { comando("k", null) }

        imgbtnpecho.setOnClickListener { comando("d", R.raw.feliz) }

        imgbtnManoDerecha.setOnClickListener { comando("e", null) }

        imgBtnFeliz.setOnClickListener { comando("f", R.raw.feliz) }

        imgBtnTriste.setOnClickListener { comando("g", R.raw.triste) }

        imgBtnSorprenddo.setOnClickListener { comando("h", R.raw.sorprendido) }

        imgBtnMalvado.setOnClickListener { comando("i", R.raw.maldad) }

        imgBtnGiño.setOnClickListener { comando("j",R.raw.gino) }

        imgbtnSalir.setOnClickListener { conectarBt.disconnect() }
        this.cargarTipografia()

    }

    private fun cargarTipografia() {
        val typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        lblControl.typeface= typeface
        lblNombreTerapistarobot.typeface= typeface
        lblNombreninorobot.typeface= typeface
        lblbtnCorrecto.typeface= typeface
        lblbtninCorrecto.typeface= typeface
        lblFeliz.typeface= typeface
        lblTriste.typeface= typeface
        lblSorprendido.typeface= typeface
        lblMalvado.typeface= typeface
        lblGiño.typeface= typeface
        lblbtnSalir.typeface= typeface
    }


    private fun cargarImagenes() {
        val camara = MetodosAyuda(this@controlRobot)
        people = intent.extras.getSerializable("Terapista") as? Terapista
        nino = intent.extras.getSerializable("Infante") as? Nino
        try {
            Glide.with(this).load(camara.buscarFoto("JPG_${people!!.imagen.toString()}")).into(imgTerapistaRobot)
            Glide.with(this).load(camara.buscarFoto("JPG_${nino!!.imagen.toString()}")).into(imgninoRobot)
        }catch (e:kotlin.KotlinNullPointerException){
            Glide.with(this).load(R.drawable.nouser).into(imgTerapistaRobot)
            Glide.with(this).load(R.drawable.nouser).into(imgninoRobot)
        }
    }

    private fun comando(comando: String, Audio: Int?){
        conectarBt.sendCommand(comando)
        if (Audio !=null){
            this.mp = MediaPlayer.create(this, Audio)
            this.mp.start()
        }
    }







}