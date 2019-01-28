package com.example.pasantiaandrade.Slider

import android.content.Context
import android.graphics.Typeface
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.Camera
import com.example.pasantiaandrade.Model.UsuarioNino
import com.example.pasantiaandrade.R
import kotlinx.android.synthetic.main.slide_ninos_listado.view.*

class slide_ninos_listado(private var context: Context, private var ListadoNinos:List<UsuarioNino>): PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return ListadoNinos.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater.inflate(R.layout.slide_ninos_listado, container, false)
        val Tipografia = Typeface.createFromAsset(context.assets, "fonts/tipografia.otf")

        view.sliderListadoNino.lblListaNinoNombre.text = this.ListadoNinos[position].TN_Nombre
        view.sliderListadoNino.lblListaNinoApellido.text = this.ListadoNinos[position].TN_Apellido
        view.sliderListadoNino.lblListaNinoEdad.text = this.ListadoNinos[position].TN_Fecha_Nac
        view.sliderListadoNino.lblListaNinoDireccion.text= this.ListadoNinos[position].TN_Direccion
        view.sliderListadoNino.lblListaTelefono.text = this.ListadoNinos[position].TN_Telefono

        view.sliderListadoNino.lblListaNinoNombre.typeface = Tipografia
        view.sliderListadoNino.lblListaNinoApellido.typeface = Tipografia
        view.sliderListadoNino.lblListaNinoEdad.typeface =Tipografia
        view.sliderListadoNino.lblListaNinoDireccion.typeface = Tipografia
        view.sliderListadoNino.lblListaTelefono.typeface = Tipografia

        try {
            Glide.with(context).load(Camera(context).Buscar_Foto("JPG_${this.ListadoNinos.get(position).TN_Imagen.toString()}")).into(view.sliderListadoNino.imgNinoLista)
        }catch (e:NullPointerException){
            Glide.with(context).load(R.drawable.nouser).into(view.sliderListadoNino.imgNinoLista)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}