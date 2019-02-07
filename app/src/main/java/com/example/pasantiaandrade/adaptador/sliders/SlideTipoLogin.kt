package com.example.pasantiaandrade.adaptador.sliders

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.pasantiaandrade.dbhelper.DBHelper

import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.master.InterfazMaster
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.slide_tipos_registro.view.*

class SlideTipoLogin(private var context: Context) : PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater
    private var listaImagenes = intArrayOf( R.drawable.terapista, R.drawable.master)
    private var titulos = arrayOf( "Terapista", "Master")
    private var textoDescripciones = arrayOf( "Acceder como Terapista", "Administrar Informacion Como Master")
    private var coloresFondo = intArrayOf(Color.rgb(125, 219, 212),Color.rgb(235, 109, 74))

    override fun getCount(): Int {
        return coloresFondo.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slide_tipos_registro, container, false)

        val typeface = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")

        view.sliderTipoRegistro.setBackgroundColor(coloresFondo[position])
        view.slideTipoRegistroImagen.setImageResource(listaImagenes[position])
        view.slideTipoRegistroTitulo.text = titulos[position]
        view.SlideTipoRegistroDescripcion.text = textoDescripciones[position]
        view.slideTipoRegistroTitulo.typeface = typeface
        view.SlideTipoRegistroDescripcion.typeface = typeface
        view.lblTituloApp.typeface = typeface
        view.lblPasswordUsuario.typeface = typeface
        view.txtPasswordUsuario.typeface = typeface
        view.btnAcceder.setOnClickListener { accederVentana(view.txtPasswordUsuario.text.toString(), position) }
        container.addView(view)
        return view
    }

    private fun accederVentana(passwordUsuario: String, position: Int) {
        if(!passwordUsuario.isEmpty()){
            when (position) {
                0 -> {
                    Toast.makeText(context, "El Password es: $passwordUsuario, para acceder como terapista", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    val usuario = DBHelper(context).getTask(passwordUsuario, "master")
                    if (usuario.nombre.isNullOrEmpty() or usuario.nombre.isNullOrBlank())
                        TastyToast.makeText(context, "Acceso Denegado", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
                    else {
                        TastyToast.makeText(context, "Acceso Consedido Master", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                        val intent = Intent(context, InterfazMaster::class.java)
                        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("master",usuario)
                        context.startActivity(intent)
                    }
                }
            }
        }else{
            TastyToast.makeText(context, "Ingrese su Password", TastyToast.LENGTH_SHORT, TastyToast.INFO)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}
