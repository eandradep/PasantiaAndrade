package com.example.pasantiaandrade.terapista

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.*
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.terapista.fragment.CrearActividadesFragment
import com.example.pasantiaandrade.terapista.fragment.DispositivoBluetooth
import com.example.pasantiaandrade.terapista.fragment.HomeFragment
import com.example.pasantiaandrade.terapista.fragment.LstNinosFragment
import kotlinx.android.synthetic.main.activity_interfaz_tera.*
import kotlinx.android.synthetic.main.nav_header_interfaz_tera.view.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InterfazTera : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var header:View? = null

    private var fragmentActual:Fragment?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz_tera)

        nav_view.setNavigationItemSelectedListener(this)

        header = nav_view.getHeaderView(0)

        cargarPerfil()

        cargarEstilos()

        displayFragment(-1)

    }

    private fun cargarEstilos() {
        val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        header!!.lblNombrePerfilTerapista.typeface =typeface
        header!!.lblTelefonoPerfilTerapista.typeface=typeface
    }

    @SuppressLint("SetTextI18n")
    private fun cargarPerfil() {
        try {
            val people : Terapista? = intent.extras.getSerializable("Terapista") as? Terapista
            header!!.lblNombrePerfilTerapista.text = "${people!!.nombre.toString()} ${people.apellido.toString()}"
            header!!.lblTelefonoPerfilTerapista.text = people.telefono.toString()
            Glide.with(this).load(MetodosAyuda(this@InterfazTera).buscarFoto("JPG_${people.imagen}")).into(header!!.imgPerfilTerapista)
        }catch (e: kotlin.KotlinNullPointerException){
            Glide.with(this).load(R.drawable.nouser).into(header!!.imgPerfilTerapista)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

     private fun displayFragment(id: Int){
        val fragment= when (id){
            R.id.btnDispositivosBT -> MetodosAyuda(this).addBundleFragment(DispositivoBluetooth(),intent.extras.getSerializable("Terapista"),"Terapista")
            R.id.btnCrearActividades -> MetodosAyuda(this).addBundleFragment(CrearActividadesFragment(),intent.extras.getSerializable("Terapista"),"Terapista")
            R.id.btnListadoNinos -> MetodosAyuda(this).addBundleFragment(LstNinosFragment(),intent.extras.getSerializable("Terapista"),"Terapista")
            else -> MetodosAyuda(this).addBundleFragment(HomeFragment(),intent.extras.getSerializable("Terapista"),"Terapista")
        }
         if (fragmentActual == null){
             fragmentActual =  fragment
             supportFragmentManager.beginTransaction().replace(R.id.RelativeLayout, fragment).commit()
         }else{
             supportFragmentManager.beginTransaction().replace(R.id.RelativeLayout, fragment).remove(fragmentActual!!).commit()
             fragmentActual =  fragment
         }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        displayFragment(item.itemId)

        when (item.itemId){
            R.id.btnLogOut -> {
                this.singOut()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun singOut() {
        val intent = Intent(this@InterfazTera, LoginUsuario::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(Intent(intent))
    }

}
