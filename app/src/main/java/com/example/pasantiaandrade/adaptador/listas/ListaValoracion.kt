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
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.modelos.Observacion
import com.example.pasantiaandrade.R
import kotlinx.android.synthetic.main.adaptador_lista_ninos.view.*

class ListaValoracion(private var mContext: Context, private var listadoObservacion:List<Observacion> ) : BaseAdapter() {



    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater  = LayoutInflater.from(mContext)
        val filaLista = layoutInflater.inflate(R.layout.adaptador_lista_ninos,parent,false)

        val tipografiaVista = Typeface.createFromAsset(mContext.assets, "fonts/tipografia.otf")

        filaLista.txtFechaValoracionNinoLista.text = Editable.Factory.getInstance().newEditable(this.listadoObservacion[position].fecha)
        filaLista.txtAreaValoraciones.text = Editable.Factory.getInstance().newEditable(this.listadoObservacion[position].observacion)
        filaLista.txtFechaValoracionNinoLista.typeface= tipografiaVista
        filaLista.txtAreaValoraciones.typeface =tipografiaVista
        try {
            Glide.with(mContext).load(MetodosAyuda(mContext).buscarFoto("JPG_${DBHelper(mContext).obtenerImagenMaster(this.listadoObservacion[position].terapistaid)}")).into(filaLista.imgTerapistaListadoNino)
        }catch (e: NullPointerException){
            Glide.with(mContext).load(R.drawable.nouser).into(filaLista.imgTerapistaListadoNino)
        }
                return filaLista
    }

    override fun getItem(position: Int): Any? {
        return "TEST"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listadoObservacion.count()
    }
}