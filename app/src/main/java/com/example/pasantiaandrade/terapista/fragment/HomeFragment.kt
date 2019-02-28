package com.example.pasantiaandrade.terapista.fragment


import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.terapista.InterfazTera
import kotlinx.android.synthetic.main.fragment_home.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private var v: View? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_home, container, false)

        this.cargarDatos((arguments!!.getSerializable("Terapista") as Terapista?)!!)



        this.cargarEstilos()

        return v
    }

    private fun cargarDatos(terapista: Terapista) {

        v!!.lblHome.text = "Bienvenid@ ${terapista!!.nombre}"
    }

    private fun cargarEstilos(){
        val typeface = Typeface.createFromAsset(activity!!.assets, "fonts/tipografia.otf")
        v!!.lblHome.typeface = typeface
        v!!.lblSbTitulo.typeface = typeface
    }


}
