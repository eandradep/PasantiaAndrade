package com.example.pasantiaandrade

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.modelos.Terapista
import kotlinx.android.synthetic.main.activity_interfaz_tera.*
import kotlinx.android.synthetic.main.app_bar_interfaz_tera.*
import kotlinx.android.synthetic.main.nav_header_interfaz_tera.view.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InterfazTera : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var header:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz_tera)
        setSupportActionBar(toolbar)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //var header2 = nav_view.menu.getItem(0)


        //(header2.setEnabled(true) as TextView).setTextColor(Color.parseColor("#2A6171"))

        header = nav_view.getHeaderView(0)


        cargarPerfil()

        cargarEstilos()



    }

    private fun cargarEstilos() {
        val typeface: Typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        header!!.lblNombrePerfilTerapista.typeface =typeface
        header!!.lblTelefonoPerfilTerapista.typeface=typeface
    }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.interfaz_tera, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.btnIniciarTerapia -> {
                // Handle the camera action
            }
            R.id.btnListadoNinos -> {

            }
            R.id.btnListadoActividades -> {

            }
            R.id.btnDispositivosBT -> {

            }
            R.id.btnEditarPerfil -> {

            }
            R.id.btnLogOut -> singOut()
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
