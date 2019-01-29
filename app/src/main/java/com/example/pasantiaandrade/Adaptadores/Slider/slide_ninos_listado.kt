package com.example.pasantiaandrade.Adaptadores.Slider

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.Adaptadores.Slider.listas_Adapter.lista_valoracion
import com.example.pasantiaandrade.Camera
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioNino
import com.example.pasantiaandrade.R
import com.github.mikephil.charting.animation.Easing
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


        view.radarCharCaracteristicasNino.webColor = Color.BLACK
        view.radarCharCaracteristicasNino.webColorInner = Color.BLACK
        view.radarCharCaracteristicasNino.webLineWidthInner = 2f
        view.radarCharCaracteristicasNino.xAxis.valueFormatter = IndexAxisValueFormatter(myArray)
        view.radarCharCaracteristicasNino.xAxis.typeface=Tipografia
        view.radarCharCaracteristicasNino.xAxis.textSize = 13f
        view.radarCharCaracteristicasNino.yAxis.setDrawLabels(false)
        view.radarCharCaracteristicasNino.description.isEnabled=false
        view.radarCharCaracteristicasNino.animateXY(3000,3000,Easing.EasingOption.EaseInOutBack,Easing.EasingOption.EaseOutSine)
        view.radarCharCaracteristicasNino.data= Grafico(Tipografia)
        view.radarCharCaracteristicasNino.legend.verticalAlignment=Legend.LegendVerticalAlignment.TOP
        view.radarCharCaracteristicasNino.legend.horizontalAlignment=Legend.LegendHorizontalAlignment.CENTER
        view.radarCharCaracteristicasNino.legend.orientation=Legend.LegendOrientation.HORIZONTAL
        view.radarCharCaracteristicasNino.legend.typeface=Tipografia
        view.radarCharCaracteristicasNino.legend.textSize=15f
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