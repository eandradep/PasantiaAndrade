package com.example.pasantiaandrade.terapista.fragment.juegos.atencionyconcetracion

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.R
import kotlinx.android.synthetic.main.activity_juego_parejas.*

class JuegoParejas : AppCompatActivity() {
    private var imgMovUno: ImageView? = null
    private var imgMovDos: ImageView? = null
    private var imgMovTres: ImageView? = null
    private var imgStaticUno: ImageView? = null
    private var imgStaticDos: ImageView? = null
    private var imgStaticTres: ImageView? = null
    private var lblTituloJuegoDos: TextView? = null
    private var rootLayout: ViewGroup? = null


    private var Mov: Array<IntArray>? = null
    private var snMov: Array<IntArray>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_parejas)

        val typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")


        rootLayout = findViewById<View>(R.id.JuegoDos) as ViewGroup
        lblTituloJuegoDos = findViewById<View>(R.id.lblTituloJuegoDos) as TextView

        lblTituloJuegoDos!!.typeface = typeface

        this.Iniciar_Imagenes_Movimiento()
        this.Iniciar_Imagenes_Staticas()

        IniciarDatos()
        this.btnSalirPares.setOnClickListener { finish() }

    }

    private fun IniciarDatos() {
        Mov = Array(3) { IntArray(2) }
        snMov = Array(3) { IntArray(2) }

        snMov!![0][0] = 3
        snMov!![1][0] = 2
        snMov!![2][0] = 1

        Mov!![0][0] = 2
        Mov!![1][0] = 1
        Mov!![2][0] = 3

        snMov!![0][1] = R.drawable.pareja1
        snMov!![1][1] = R.drawable.pareja2
        snMov!![2][1] = R.drawable.pareja3

        Glide.with(this).load(snMov!![0][1]).into(imgStaticUno!!)
        Glide.with(this).load(snMov!![1][1]).into(imgStaticDos!!)
        Glide.with(this).load(snMov!![2][1]).into(imgStaticTres!!)


        Mov!![0][1] = R.drawable.pareja_3
        Mov!![1][1] = R.drawable.pareja_1
        Mov!![2][1] = R.drawable.pareja_2

        Glide.with(this).load(Mov!![0][1]).into(imgMovUno!!)
        Glide.with(this).load(Mov!![1][1]).into(imgMovDos!!)
        Glide.with(this).load(Mov!![2][1]).into(imgMovTres!!)

    }

    private fun CalcularCordenadas(numero: Int) {
        if (numero == R.id.imgMovUno) {
            Log.d("Valor en X", "" + imgMovUno!!.x)
            Log.d("Valor en Y", "" + imgMovUno!!.y)
            Choque("Imagen 1", Mov!![0][0], imgMovUno!!.x, imgMovUno!!.y, imgMovUno!!.x + imgMovUno!!.width, imgMovUno!!.y + imgMovUno!!.height)
        }

        if (numero == R.id.imgMovDos) {
            Log.d("Valor en X", "" + imgMovDos!!.x)
            Log.d("Valor en Y", "" + imgMovDos!!.y)
            Choque("Imagen 2", Mov!![1][0], imgMovDos!!.x, imgMovDos!!.y, imgMovDos!!.x + imgMovDos!!.width, imgMovDos!!.y + imgMovDos!!.height)
        }

        if (numero == R.id.imgMovTres) {
            Log.d("Valor en X", "" + imgMovTres!!.x)
            Log.d("Valor en Y", "" + imgMovTres!!.y)
            Choque("Imagen 3", Mov!![2][0], imgMovTres!!.x, imgMovTres!!.y, imgMovTres!!.x + imgMovTres!!.width, imgMovTres!!.y + imgMovTres!!.height)
        }
    }

    fun Choque(nombre: String, codigo: Int, x1: Float, x2: Float, x3: Float, x4: Float) {
        if (codigo == snMov!![0][0]) {
            Log.d("La " + nombre + "debe", "Chocar con imagen 1")
        }
        if (codigo == snMov!![1][0]) {
            Log.d("La " + nombre + "debe", "Chocar con imagen 2")
        }
        if (codigo == snMov!![2][0]) {
            Log.d("La " + nombre + "debe", "Chocar con imagen 3")
        }
        val x = (imgStaticUno!!.y + imgStaticUno!!.width).toDouble()
        if (x2 < x) {
            Toast.makeText(this,"incorrecto",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"incorrecto",Toast.LENGTH_SHORT).show()
        }
    }

    //Se Inician las Imagenes Estaticas y con Movimiento
    private fun Iniciar_Imagenes_Staticas() {
        imgStaticUno = findViewById<View>(R.id.imgStaticUno) as ImageView
        imgStaticDos = findViewById<View>(R.id.imgStaticDos) as ImageView
        imgStaticTres = findViewById<View>(R.id.imgStaticTres) as ImageView
    }

    private fun Iniciar_Imagenes_Movimiento() {
        imgMovUno = findViewById<View>(R.id.imgMovUno) as ImageView
        imgMovDos = findViewById<View>(R.id.imgMovDos) as ImageView
        imgMovTres = findViewById<View>(R.id.imgMovTres) as ImageView
        val m = MovimientosPantalla()
        imgMovUno!!.setOnTouchListener(m)
        imgMovDos!!.setOnTouchListener(m)
        imgMovTres!!.setOnTouchListener(m)
    }



    //Clase Implementada para Permitir el Movimiento de las Figuras.
    internal inner class MovimientosPantalla : View.OnTouchListener {
        private var _xDelta: Int = 0
        private var _yDelta: Int = 0

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val X = event.rawX.toInt()
            val Y = event.rawY.toInt()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as RelativeLayout.LayoutParams
                    _xDelta = X - lParams.leftMargin
                    _yDelta = Y - lParams.topMargin
                }
                MotionEvent.ACTION_UP -> when (view.id) {
                    R.id.imgMovDos -> CalcularCordenadas(R.id.imgMovDos)
                    R.id.imgMovUno -> CalcularCordenadas(R.id.imgMovUno)
                    R.id.imgMovTres -> CalcularCordenadas(R.id.imgMovTres)
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                }
                MotionEvent.ACTION_POINTER_UP -> {
                }
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view
                            .layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = X - _xDelta
                    layoutParams.topMargin = Y - _yDelta
                    layoutParams.rightMargin = -250
                    layoutParams.bottomMargin = -250
                    view.layoutParams = layoutParams
                }
            }
            rootLayout!!.invalidate()
            return true
        }
    }

}
