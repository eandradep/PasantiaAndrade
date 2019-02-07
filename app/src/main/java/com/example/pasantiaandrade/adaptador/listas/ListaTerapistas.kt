package com.example.pasantiaandrade.adaptador.listas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.master.terapista.ListadoTerapistas
import kotlinx.android.synthetic.main.adaptador_lista_ninos.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import com.example.pasantiaandrade.R



class ListaTerapistas(private var mContext: Context, private var listadoTerapistas:List<Terapista> ): BaseAdapter(){


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater  = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(R.layout.adaptador_lista_ninos,parent,false)
        val typeface = Typeface.createFromAsset(mContext.assets, "fonts/tipografia.otf")
        view.txtFechaValoracionNinoLista.text = Editable.Factory.getInstance().newEditable("${listadoTerapistas[position].nombre.toString()} ${listadoTerapistas[position].apellido.toString()}")
        view.txtAreaValoraciones.text = Editable.Factory.getInstance().newEditable(listadoTerapistas[position].telefono.toString())
        try {
            Glide.with(mContext).load(MetodosAyuda(mContext).buscarFoto("JPG_${listadoTerapistas[position].imagen.toString()}")).into(view.imgTerapistaListadoNino)
        }catch (e: NullPointerException){
            Glide.with(mContext).load(R.drawable.nouser).into(view.imgTerapistaListadoNino)
        }
        view.txtFechaValoracionNinoLista.isEnabled= false
        view.txtAreaValoraciones.isEnabled=false
        view.txtFechaValoracionNinoLista.typeface=typeface
        view.txtAreaValoraciones.typeface = typeface
        view.onClick { (mContext as ListadoTerapistas).validarCredenciales(listadoTerapistas[position]) }
        return view
    }

    override fun getItem(position: Int): Any {
        return "TEST"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.listadoTerapistas.count()
    }

}
