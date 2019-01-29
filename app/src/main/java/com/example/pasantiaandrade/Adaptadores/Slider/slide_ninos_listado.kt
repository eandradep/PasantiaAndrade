package com.example.pasantiaandrade.Adaptadores.Slider

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.Adaptadores.Slider.listas_Adapter.lista_valoracion
import com.example.pasantiaandrade.Camera
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.Model.UsuarioNino
import com.example.pasantiaandrade.R
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.slide_ninos_listado.view.*
import kotlin.random.Random

class slide_ninos_listado(private var context: Context, private var ListadoNinos:List<UsuarioNino>): PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return ListadoNinos.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    val myArray = arrayOf("M Fina", "M. Gruesa","Habilidad 1","Habilidad 2","Habilidad 3")

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater.inflate(R.layout.slide_ninos_listado, container, false)
        val Tipografia = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")

        view.sliderListadoNino.lblListaNinoNombre.text = this.ListadoNinos[position].TN_Nombre
        view.sliderListadoNino.lblListaNinoApellido.text = this.ListadoNinos[position].TN_Apellido
        view.sliderListadoNino.lblListaNinoEdad.text = this.ListadoNinos[position].TN_Fecha_Nac
        view.sliderListadoNino.lblListaNinoDireccion.text= this.ListadoNinos[position].TN_Direccion
        view.sliderListadoNino.lblListaTelefono.text = this.ListadoNinos[position].TN_Telefono

        view.lstObservacionesNinos.adapter = lista_valoracion(context, DBHelper(context).ObtenerValroaciones(this.ListadoNinos[position].TN_id) )

        var radarDataSet = RadarDataSet(Valores(),"Habilidades del Nino")
        var radarData:RadarData?= RadarData()
        radarData!!.addDataSet(radarDataSet)
        view.radarCharCaracteristicasNino.xAxis.valueFormatter = IndexAxisValueFormatter(myArray)
        view.radarCharCaracteristicasNino.data=radarData
        view.radarCharCaracteristicasNino.invalidate()

        view.sliderListadoNino.lblListaNinoNombre.typeface = Tipografia
        view.sliderListadoNino.lblListaNinoApellido.typeface = Tipografia
        view.sliderListadoNino.lblListaNinoEdad.typeface =Tipografia
        view.sliderListadoNino.lblListaNinoDireccion.typeface = Tipografia
        view.sliderListadoNino.lblListaTelefono.typeface = Tipografia

        try {
            Glide.with(context).load(Camera(context).Buscar_Foto("JPG_${this.ListadoNinos.get(position).TN_Imagen.toString()}")).into(view.sliderListadoNino.imgNinoLista)
        }catch (e: NullPointerException){
            Glide.with(context).load(R.drawable.nouser).into(view.sliderListadoNino.imgNinoLista)
        }
        container.addView(view)
        return view
    }
    fun Valores():ArrayList<RadarEntry>{
        var listado :ArrayList<RadarEntry> = ArrayList<RadarEntry>()
        for (i  in myArray){
            listado.add(RadarEntry((0..20).random().toFloat()))
        }
        return listado
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}