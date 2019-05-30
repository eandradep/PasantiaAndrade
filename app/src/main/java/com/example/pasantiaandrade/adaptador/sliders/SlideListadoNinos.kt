package com.example.pasantiaandrade.adaptador.sliders

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.LoginUsuario
import com.example.pasantiaandrade.adaptador.listas.ListaValoracion
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.modelos.Nino
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.terapista.fragment.HomeFragment
import com.example.pasantiaandrade.terapista.fragment.LstNinosFragment
import com.example.pasantiaandrade.terapista.fragment.juegos.SeleccionJuegos
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.RadarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.slide_ninos_listado.view.*
import java.util.*


@Suppress("DEPRECATION")
class SlideListadoNinos(private var context: Context, private var listadoNinos:List<Nino>, private var activarBoton:Boolean, private var terapista: Terapista?): PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return listadoNinos.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    private val myArray = arrayOf("T . V", "T . A","A & C","P","M . F", "M . G")
    private var radarCharCaracteristicasNino:RadarChart? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater.inflate(R.layout.slide_ninos_listado, container, false)
        val typeface = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")

        view.sliderListadoNino.lblListaNinoNombre.text = this.listadoNinos[position].nombre
        view.sliderListadoNino.lblListaNinoApellido.text = this.listadoNinos[position].apellido
        val separacionDatos= this.listadoNinos[position].fechanacimiento.toString().split("/")
        view.sliderListadoNino.lblListaNinoEdad.text = MetodosAyuda(context).calcularEdad(GregorianCalendar(separacionDatos[2].toInt(), separacionDatos[1].toInt(), separacionDatos[0].toInt()))
        view.sliderListadoNino.lblListaNinoDireccion.text= this.listadoNinos[position].direccion
        view.sliderListadoNino.lblListaTelefono.text = this.listadoNinos[position].telefono
        if (activarBoton){
            view.btnSalirListadoNinos.setOnClickListener { (context as Activity).finish() }
        }else{
            view.btnSalirListadoNinos.setOnClickListener {
                (context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.RelativeLayout, MetodosAyuda(context).addBundleFragment(HomeFragment(),terapista, "Terapista")).remove(LstNinosFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit() }
            view.sliderListadoNino.imgNinoLista.setOnClickListener {
                view.context.startActivity(Intent(context, SeleccionJuegos::class.java).putExtra("MasterID",this.listadoNinos[position].id))
            }
        }

        view.lstObservacionesNinos.adapter = ListaValoracion(context, DBHelper(context).listadoOberservacion(this.listadoNinos[position].id))

        aplicarGrafica(typeface,view.radarCharCaracteristicasNino)

        view.sliderListadoNino.lblListaNinoNombre.typeface = typeface
        view.sliderListadoNino.lblListaNinoApellido.typeface = typeface
        view.sliderListadoNino.lblListaNinoEdad.typeface =typeface
        view.sliderListadoNino.lblListaNinoDireccion.typeface = typeface
        view.sliderListadoNino.lblListaTelefono.typeface = typeface

        try {
            Glide.with(context).load(MetodosAyuda(context).buscarFoto("JPG_${this.listadoNinos[position].imagen.toString()}")).into(view.sliderListadoNino.imgNinoLista)
        }catch (e: NullPointerException){
            Glide.with(context).load(R.drawable.nouser).into(view.sliderListadoNino.imgNinoLista)
        }
        container.addView(view)
        return view
    }

    private fun aplicarGrafica(tipografia: Typeface, radarCharCaracteristicasNino: RadarChart) {
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
        this.radarCharCaracteristicasNino!!.data= personalizarGrafico(tipografia)
        this.radarCharCaracteristicasNino!!.legend.verticalAlignment=Legend.LegendVerticalAlignment.TOP
        this.radarCharCaracteristicasNino!!.legend.horizontalAlignment=Legend.LegendHorizontalAlignment.CENTER
        this.radarCharCaracteristicasNino!!.legend.orientation=Legend.LegendOrientation.HORIZONTAL
        this.radarCharCaracteristicasNino!!.legend.typeface=tipografia
        this.radarCharCaracteristicasNino!!.legend.textSize=15f
        this.radarCharCaracteristicasNino!!.invalidate()
    }

    private fun personalizarGrafico(tipografia: Typeface):RadarData?{

        val radarDataSet = RadarDataSet(cargarValores(),"Habilidades")
        radarDataSet.setDrawFilled(true)
        radarDataSet.lineWidth=3f
        radarDataSet.color=Color.GRAY
        radarDataSet.fillColor = Color.GRAY
        radarDataSet.valueTypeface=tipografia
        val radarData:RadarData?= RadarData()
        radarData!!.addDataSet(radarDataSet)
        return radarData
    }

    private fun cargarValores():ArrayList<RadarEntry>{
        val listado :ArrayList<RadarEntry> = ArrayList()
        for (i  in myArray){
            listado.add(RadarEntry((0..10).random().toFloat()))
        }
        return listado
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }




}