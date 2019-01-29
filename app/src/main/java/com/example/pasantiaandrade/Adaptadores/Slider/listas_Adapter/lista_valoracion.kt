package com.example.pasantiaandrade.Adaptadores.Slider.listas_Adapter

import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.Camera
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.R
import kotlinx.android.synthetic.main.activity_interfaz_master.*
import kotlinx.android.synthetic.main.adaptador_lista_ninos.view.*

class lista_valoracion(private var context: Context, private var ListadoObservacion:List<Tab_Observacion> ) : BaseAdapter() {

private val mContext:Context
    init {
        mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater  = LayoutInflater.from(mContext)
        val RowMain = layoutInflater.inflate(R.layout.adaptador_lista_ninos,parent,false)

        RowMain.txtFechaValoracionNinoLista.text = Editable.Factory.getInstance().newEditable(this.ListadoObservacion[position].TV_Fecha)
        RowMain.txtAreaValoraciones.text = Editable.Factory.getInstance().newEditable(this.ListadoObservacion[position].TV_Observacion)
        try {
            Glide.with(context).load(Camera(context).Buscar_Foto("JPG_${DBHelper(context).ObtenerImagenMaster(this.ListadoObservacion[position].TV_TM_ID)}")).into(RowMain.imgTerapistaListadoNino)
        }catch (e: NullPointerException){
            Glide.with(context).load(R.drawable.nouser).into(RowMain.imgTerapistaListadoNino)
        }
                return RowMain
    }

    override fun getItem(position: Int): Any? {
        return "TEST"
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return ListadoObservacion.count()
    }
}