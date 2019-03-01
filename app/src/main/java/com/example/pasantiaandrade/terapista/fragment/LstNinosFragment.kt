package com.example.pasantiaandrade.terapista.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.adaptador.sliders.SlideListadoNinos
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.modelos.Terapista

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 *
 */
class LstNinosFragment : Fragment() {

    private lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v: View? = inflater.inflate(R.layout.fragment_lst_ninos, container, false)
        viewPager = v!!.findViewById(R.id.viewPageListadoNinoFragemnt)
        var terapista:Terapista =(arguments!!.getSerializable("Terapista") as Terapista?)!!
        viewPager.adapter = SlideListadoNinos(v.context, DBHelper(v.context).listadoNinos,false,terapista)

        return v
    }


}
