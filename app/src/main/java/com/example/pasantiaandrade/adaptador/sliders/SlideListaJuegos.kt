package com.example.pasantiaandrade.adaptador.sliders

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.terapista.fragment.juegos.atencionyconcetracion.JuegoParejas
import com.example.pasantiaandrade.terapista.fragment.juegos.control.controlRobot
import com.example.pasantiaandrade.terapista.fragment.juegos.verbal.RepetirPalabras
import kotlinx.android.synthetic.main.slide_lista_juegos.view.*

class SlideListaJuegos(private var context: Context) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null
    var listaImagenes = intArrayOf(R.drawable.image_7, R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5, R.drawable.image_6)
    var listaTitulos = arrayOf(" CONTROL REMOTO ", " TERAPIA VERBAL ", " APRENDIZAJE ", " ATENCION Y CONCENTRACION ", " PERCEPCION ", " MOTRICIDAD GRUESA ", " MOTRICIDAD FINA ")
    var listaDescripcion = arrayOf(" PERMITE EL CONTROL TOTAL DEL ROBOT ", " REPETIR PALABRAS ", " EN ESTA SECCION SE PUEDE ENCONTRAR CONJUNTOS, POSICIONES DE FIGURAS ", " BUSQUEDA DE PAREJAS ", " PARTES DEL CUERPO, VESTIR A UN HOMBRE O MUJER ", " ACTIVIDADES DE MOVIMIENTO DEL CUERPO ", " CREAR SECUENCIAS DE SONIDOS ")
    var coloresFondo = intArrayOf(Color.rgb(55, 55, 55), Color.rgb(239, 85, 85), Color.rgb(110, 49, 89), Color.rgb(55, 55, 55), Color.rgb(239, 85, 85), Color.rgb(110, 49, 89), Color.rgb(55, 55, 55))

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    override fun getCount(): Int {
        return coloresFondo.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slide_lista_juegos, container, false)
        view.sliderListaJuego.setBackgroundColor(coloresFondo[position])
        view.lblrecomendacionTipo.text = listaTitulos[position]
        view.lblrecomendacionDescricpion.text= listaDescripcion[position]
        Glide.with(context).load(listaImagenes[position]).into(view.imgsliderecomendacion)
        val typeface = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")
        view.lblrecomendacionTipo.typeface = typeface
        view.lblrecomendacionDescricpion.typeface =typeface
        view.setOnClickListener {
            when (position) {
                0 -> view.context.startActivity(Intent(context, controlRobot::class.java).putExtra("CodigoNino","1"))
                1 -> view.context.startActivity(Intent(context, RepetirPalabras::class.java).putExtra("CodigoNino","1"))
                2 -> Toast.makeText(context, "Elemento 3", Toast.LENGTH_SHORT).show()
                3 -> view.context.startActivity(Intent(context, JuegoParejas::class.java).putExtra("CodigoNino","1"))
                4 -> Toast.makeText(context, "Elemento 5", Toast.LENGTH_SHORT).show()
                5 -> Toast.makeText(context, "Elemento 6", Toast.LENGTH_SHORT).show()
                6 -> Toast.makeText(context, "Elemento 7", Toast.LENGTH_SHORT).show()
            }
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}