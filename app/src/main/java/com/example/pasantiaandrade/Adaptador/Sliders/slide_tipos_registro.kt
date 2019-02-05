package com.example.pasantiaandrade.Adaptador.Sliders

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
import com.example.pasantiaandrade.DBHelper.DBHelper

import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.Master.InterfazMaster
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.slide_tipos_registro.view.*

class slide_tipos_registro(private var context: Context) : PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    private lateinit var db:DBHelper

    //Lista de las Imagenes

    var lista_Imagenes = intArrayOf( R.drawable.terapista, R.drawable.master)

    //Lista de los Titulos Y Descripciones

    var titulos = arrayOf( "Terapista", "Master")

    //Descripcion

    var Descripciones = arrayOf( "Acceder como Terapista", "Administrar Informacion Como Master")

    var Colores_Fondo = intArrayOf(Color.rgb(125, 219, 212),Color.rgb(235, 109, 74))

    override fun getCount(): Int {
        return Colores_Fondo.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slide_tipos_registro, container, false)

        val Tipografia = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")

        view.sliderTipoRegistro.setBackgroundColor(Colores_Fondo[position])
        view.slideTipoRegistroImagen.setImageResource(lista_Imagenes[position])
        view.slideTipoRegistroTitulo.text = titulos[position]
        view.SlideTipoRegistroDescripcion.text = Descripciones[position]

        // Aplicar estilos
        view.slideTipoRegistroTitulo.typeface = Tipografia
        view.SlideTipoRegistroDescripcion.typeface = Tipografia
        view.lblTituloApp.typeface = Tipografia
        view.lblPasswordUsuario.typeface = Tipografia
        view.txtPasswordUsuario.typeface = Tipografia

        view.btnAcceder.setOnClickListener { AccederVentana(view.txtPasswordUsuario.text.toString(), position) }


        container.addView(view)
        return view
    }

    private fun AccederVentana(passwordUsuario: String, position: Int) {

        db = DBHelper(context)

        if(!passwordUsuario.isEmpty()){
            when (position) {
                0 -> {
                    Toast.makeText(context, "El Password es: $passwordUsuario, para acceder como terapista", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    var usuario = db.getTask(passwordUsuario, "master")
                    if (usuario.TM_Nombre.isNullOrEmpty() or usuario.TM_Nombre.isNullOrBlank())
                        TastyToast.makeText(context, "Acceso Denegado", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    else {
                        TastyToast.makeText(context, "Acceso Consedido Master", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                        val intent = Intent(context, InterfazMaster::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("master",usuario)
                        context.startActivity(intent)
                    }
                }
            }
        }else{
            TastyToast.makeText(context, "Ingrese su Password", TastyToast.LENGTH_SHORT, TastyToast.INFO);
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

}
