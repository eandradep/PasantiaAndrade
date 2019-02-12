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
import com.example.pasantiaandrade.dbhelper.DBHelper

import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.TerapistaInterfaz
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
                0 ->cargarVentana(passwordUsuario,"Terapista",null,TerapistaInterfaz::class.java)
                1 ->cargarVentana(passwordUsuario,"Master",InterfazMaster::class.java,null)
            }
        }else{
            TastyToast.makeText(context, "Ingrese su Password", TastyToast.LENGTH_SHORT, TastyToast.INFO)
        }
    }

    private fun cargarVentana(userPassword: String, userTipo: String, java: Class<InterfazMaster>?, java1: Class<TerapistaInterfaz>?) {
        val usuario = DBHelper(context).getTask(userPassword, userTipo)
        if (usuario.nombre.isNullOrEmpty() or usuario.nombre.isNullOrBlank())
            TastyToast.makeText(context, "Acceso Denegado", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        else {
            TastyToast.makeText(context, "Acceso Consedido $userTipo", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            val intent = if (java == null)
                Intent(context, java1)
            else
                Intent(context, java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(userTipo,usuario)
            context.startActivity(intent)
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}
