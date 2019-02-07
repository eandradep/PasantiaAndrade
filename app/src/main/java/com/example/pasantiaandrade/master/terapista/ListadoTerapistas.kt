package com.example.pasantiaandrade

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.WindowManager
import com.example.pasantiaandrade.adaptador.listas.ListaTerapistas
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioMT
import kotlinx.android.synthetic.main.activity_listado_terapistas.*

class ListadoTerapistas : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_terapistas)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        listViewTerapistas.adapter = ListaTerapistas(this,DBHelper(this).allusers)
        aplicarTipografia()

        btnCerrarListadonTerapista.setOnClickListener { finish() }

    }

    fun cargarElementos(position: UsuarioMT){
        lblIdListaTerapista.text = "${position.TM_Nombre}"
        lblTipoListaTerapista.text = position.TM_Tipo
        txtNombreListaTerapista.text = Editable.Factory.getInstance().newEditable(position.TM_Nombre.toString())
        txtApellidoListaTerapista.text = Editable.Factory.getInstance().newEditable(position.TM_Apellido.toString())
        txtTelefonoListaTerapista.text = Editable.Factory.getInstance().newEditable(position.TM_Telefono.toString())
        txtPasswordListaTerapista.text = Editable.Factory.getInstance().newEditable(position.TM_Password.toString())
    }

    private fun aplicarTipografia(){
        val typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        lblIdListaTerapista.typeface=typeface
        lblTipoListaTerapista.typeface=typeface
        txtNombreListaTerapista.typeface=typeface
        txtApellidoListaTerapista.typeface = typeface
        txtTelefonoListaTerapista.typeface= typeface
        txtPasswordListaTerapista.typeface=typeface
        txtPasswordCListaTerapista.typeface=typeface
        lblFotoTerapistaLista.typeface=typeface
        lblGuardarTerapistaLista.typeface=typeface
        lblCerrarListadonTerapista.typeface=typeface
    }

}

