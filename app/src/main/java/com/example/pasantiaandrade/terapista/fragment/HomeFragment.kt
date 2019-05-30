package com.example.pasantiaandrade.terapista.fragment


import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.modelos.Terapista
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.Serializable


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private var v: View? =null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.fragment_home, container, false)

        this.cargarDatos((arguments!!.getSerializable("Terapista") as Terapista?)!!)

        this.v!!.imgBtnConfigurarBluetooth.setOnClickListener { this.cambiarFragment(DispositivoBluetooth()) }
        this.v!!.imgListadoNino.setOnClickListener { this.cambiarFragment(LstNinosFragment()) }
        this.v!!.imgbtnCrearActividad.setOnClickListener { this.cambiarFragment(CrearActividadesFragment()) }

        this.cargarEstilos()

        return v
    }

    @SuppressLint("SetTextI18n")
    private fun cargarDatos(terapista: Terapista) {
        v!!.lblHome.text = "Bienvenid@ ${terapista.nombre}"
    }

    private fun cambiarFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.RelativeLayout, MetodosAyuda(activity!!).addBundleFragment(fragment,(arguments!!.getSerializable("Terapista") as Terapista?)!!,"Terapista")).remove(this).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
    }

    private fun cargarEstilos(){
        val typeface = Typeface.createFromAsset(activity!!.assets, "fonts/tipografia.otf")
        v!!.lblHome.typeface = typeface
        v!!.lblSbTitulo.typeface = typeface
        v!!.lblMensajePrincipal.typeface=typeface
        v!!.lblListadoNino.typeface=typeface
        v!!.lblCrearActividad.typeface=typeface
        v!!.lblConfigurarBt.typeface=typeface
        v!!.lblMensajeArrastre.typeface=typeface

    }




}
