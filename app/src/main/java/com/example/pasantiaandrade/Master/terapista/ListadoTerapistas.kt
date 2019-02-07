package com.example.pasantiaandrade.master.terapista

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.adaptador.listas.ListaTerapistas
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.R
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_listado_terapistas.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ListadoTerapistas : AppCompatActivity(){

    private val requestImage = 1
    private var fotoTemporal : String? = null
    private var editarTerapista:Boolean?= false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_terapistas)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        listViewTerapistas.adapter = ListaTerapistas(this,DBHelper(this).allusers)
        aplicarTipografia()
        btnCerrarListadonTerapista.setOnClickListener { finish() }
        btnTomarFotoTerapistaLista.setOnClickListener { dispatchTakePictureIntent() }
        btnGuardarCambiosTerapistaLista.setOnClickListener { actualizarTerapista() }

    }

    private fun actualizarTerapista() {
        if (editarTerapista == true )
            if (txtPasswordCListaTerapista.text.toString() == txtPasswordListaTerapista.text.toString()) {
                DBHelper(this).updateUser(Terapista(lblIdListaTerapista.text.toString().toInt(), txtNombreListaTerapista.text.toString(), txtApellidoListaTerapista.text.toString(), lblTipoListaTerapista.text.toString(), fotoTemporal.toString(), txtTelefonoListaTerapista.text.toString(), txtPasswordCListaTerapista.text.toString()))
                listViewTerapistas.adapter = ListaTerapistas(this,DBHelper(this).allusers)
                lipiarValores()
            }else
                TastyToast.makeText(this@ListadoTerapistas, "Confirmar Contrasena", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        else
            TastyToast.makeText(this@ListadoTerapistas, "Seleccione un Usuario de la Lista", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
    }

    private fun lipiarValores() {
        lblIdListaTerapista.text = ""
        lblTipoListaTerapista.text = ""
        txtNombreListaTerapista.text = Editable.Factory.getInstance().newEditable("")
        txtApellidoListaTerapista.text = Editable.Factory.getInstance().newEditable("")
        txtTelefonoListaTerapista.text = Editable.Factory.getInstance().newEditable("")
        txtPasswordListaTerapista.text = Editable.Factory.getInstance().newEditable("")
        fotoTemporal = null
        editarTerapista = false
        Glide.with(this@ListadoTerapistas).load(R.drawable.nouser).into(imgListaTerapista)
    }

    private fun dispatchTakePictureIntent() {
        if (editarTerapista==true)
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImage)
            }
        }
        else
            TastyToast.makeText(this@ListadoTerapistas, "Seleccione un Usuario de la Lista", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cam = MetodosAyuda(this)
        if (requestCode == requestImage && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            fotoTemporal = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$fotoTemporal")
            Glide.with(this).load(cam.buscarFoto("JPG_$fotoTemporal")).into(imgListaTerapista)
        }
    }
    fun validarCredenciales(terapista: Terapista){
        if (terapista.id.toString().toInt() == intent.extras.getInt("MasterID")){
            TastyToast.makeText(this@ListadoTerapistas, "Puedes Editar Tu Perfil desde la Pantalla de Inicio", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }else{
            if (terapista.tipo == "master"){
                if (intent.extras.getInt("MasterID") == 1){
                    cargarDatos(terapista)
                }
                else {
                    TastyToast.makeText(this@ListadoTerapistas, "Solo El Usuario Administrador Puede Editar Estos Datos", TastyToast.LENGTH_SHORT, TastyToast.INFO)
                }
            }else{
                cargarDatos(terapista)
            }
        }
    }

    private fun cargarDatos(terapista: Terapista){
        editarTerapista = true
        lblIdListaTerapista.text = "${terapista.id}"
        lblTipoListaTerapista.text = terapista.tipo
        txtNombreListaTerapista.text = Editable.Factory.getInstance().newEditable(terapista.nombre.toString())
        txtApellidoListaTerapista.text = Editable.Factory.getInstance().newEditable(terapista.apellido.toString())
        txtTelefonoListaTerapista.text = Editable.Factory.getInstance().newEditable(terapista.telefono.toString())
        txtPasswordListaTerapista.text = Editable.Factory.getInstance().newEditable(terapista.password.toString())
        fotoTemporal=terapista.imagen.toString()
        try {
            Glide.with(this@ListadoTerapistas).load(MetodosAyuda(this@ListadoTerapistas).buscarFoto("JPG_$fotoTemporal")).into(imgListaTerapista)
        }catch (e: NullPointerException){
            Glide.with(this@ListadoTerapistas).load(R.drawable.nouser).into(imgListaTerapista)
        }
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

