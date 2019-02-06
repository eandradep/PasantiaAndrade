package com.example.pasantiaandrade.Adaptador.Listas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pasantiaandrade.Model.UsuarioMT
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.listadoTerapistas
import org.jetbrains.anko.sdk27.coroutines.onClick

class ListadoTerapistas1(private var mContext: Context, private var ListadoTerapistas:List<UsuarioMT> ): BaseAdapter(){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater  = LayoutInflater.from(mContext)
        val RowMain = layoutInflater.inflate(R.layout.adaptador_lista_ninos,parent,false)
        RowMain.onClick { listadoTerapistas().mostrar(position) }
        return RowMain
    }

    override fun getItem(position: Int): Any {
        return "TEST"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return ListadoTerapistas.count()
    }

}
