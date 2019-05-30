package com.example.pasantiaandrade.adaptador.listas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.modelos.DispositivoBluetooth
import kotlinx.android.synthetic.main.adaptador_lista_dispositivos.view.*

class ListaDispositivos(private var mContext: Context, private var listadoDispositivos:List<DispositivoBluetooth>):BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater  = LayoutInflater.from(mContext)
        val filaLista = layoutInflater.inflate(R.layout.adaptador_lista_dispositivos,parent,false)
        filaLista.lblIdDispositivo.text = listadoDispositivos[position].dispositivoNombre.toString()
        return filaLista
    }

    override fun getItem(position: Int): Any {
        return "TEST"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listadoDispositivos.count()
    }
}