package com.example.pasantiaandrade.Adaptador.Sliders

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.Adaptador.Listas.lista_valoracion
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioNino
import com.example.pasantiaandrade.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.slide_ninos_listado.view.*

class slide_ninos_listado(private var context: Context, private var ListadoNinos:List<UsuarioNino>): PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return ListadoNinos.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    val myArray = arrayOf("T . V", "T . A","A & C","P","M . F", "M . G")
    var radarCharCaracteristicasNino:RadarChart? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater.inflate(R.layout.slide_ninos_listado, container, false)
        val Tipografia = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")

        view.sliderListadoNino.lblListaNinoNombre.text = this.ListadoNinos[position].TN_Nombre
        view.sliderListadoNino.lblListaNinoApellido.text = this.ListadoNinos[position].TN_Apellido
        view.sliderListadoNino.lblListaNinoEdad.text = this.ListadoNinos[position].TN_Fecha_Nac
        view.sliderListadoNino.lblListaNinoDireccion.text= this.ListadoNinos[position].TN_Direccion
        view.sliderListadoNino.lblListaTelefono.text = this.ListadoNinos[position].TN_Telefono

        view.lstObservacionesNinos.adapter = lista_valoracion(context, DBHelper(context).ListadoOberservacion(this.ListadoNinos[position].TN_id))

        AplicarGrafica(Tipografia,view.radarCharCaracteristicasNino)

        view.sliderListadoNino.lblListaNinoNombre.typeface = Tipografia
        view.sliderListadoNino.lblListaNinoApellido.typeface = Tipografia
        view.sliderListadoNino.lblListaNinoEdad.typeface =Tipografia
        view.sliderListadoNino.lblListaNinoDireccion.typeface = Tipografia
        view.sliderListadoNino.lblListaTelefono.typeface = Tipografia

        try {
            Glide.with(context).load(MetodosAyuda(context).Buscar_Foto("JPG_${this.ListadoNinos.get(position).TN_Imagen.toString()}")).into(view.sliderListadoNino.imgNinoLista)
        }catch (e: NullPointerException){
            Glide.with(context).load(R.drawable.nouser).into(view.sliderListadoNino.imgNinoLista)
        }
        container.addView(view)
        return view
    }

    private fun AplicarGrafica(tipografia: Typeface, radarCharCaracteristicasNino: RadarChart) {
        this.radarCharCaracteristicasNino = radarCharCaracteristicasNino
        this.radarCharCaracteristicasNino!!.webColor = Color.BLACK
        this.radarCharCaracteristicasNino!!.webColorInner = Color.BLACK
        this.radarCharCaracteristicasNino!!.webLineWidthInner = 2f
        this.radarCharCaracteristicasNino!!.xAxis.valueFormatter = IndexAxisValueFormatter(myArray)
        this.radarCharCaracteristicasNino!!.xAxis.typeface=tipografia
        this.radarCharCaracteristicasNino!!.xAxis.textSize = 13f
        this.radarCharCaracteristicasNino!!.yAxis.setDrawLabels(false)
        this.radarCharCaracteristicasNino!!.description.isEnabled=false
        this.radarCharCaracteristicasNino!!.animateXY(3000,3000,Easing.EasingOption.EaseInOutBack,Easing.EasingOption.EaseOutSine)
        this.radarCharCaracteristicasNino!!.data= Grafico(tipografia)
        this.radarCharCaracteristicasNino!!.legend.verticalAlignment=Legend.LegendVerticalAlignment.TOP
        this.radarCharCaracteristicasNino!!.legend.horizontalAlignment=Legend.LegendHorizontalAlignment.CENTER
        this.radarCharCaracteristicasNino!!.legend.orientation=Legend.LegendOrientation.HORIZONTAL
        this.radarCharCaracteristicasNino!!.legend.typeface=tipografia
        this.radarCharCaracteristicasNino!!.legend.textSize=15f
        this.radarCharCaracteristicasNino!!.invalidate()
    }

    fun Grafico(tipografia: Typeface):RadarData?{

        var radarDataSet = RadarDataSet(Valores(),"Habilidades")
        radarDataSet.setDrawFilled(true)
        radarDataSet.lineWidth=3f
        radarDataSet.color=Color.GRAY
        radarDataSet.fillColor = Color.GRAY
        radarDataSet.valueTypeface=tipografia
        var radarData:RadarData?= RadarData()
        radarData!!.addDataSet(radarDataSet)
        return radarData
    }

    fun Valores():ArrayList<RadarEntry>{
        var listado :ArrayList<RadarEntry> = ArrayList<RadarEntry>()
        for (i  in myArray){
            listado.add(RadarEntry((0..10).random().toFloat()))
        }
        return listado
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}