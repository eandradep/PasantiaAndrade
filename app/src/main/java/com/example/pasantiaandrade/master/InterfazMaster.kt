package com.example.pasantiaandrade.master

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.master.nino.ListadoNinos
import com.example.pasantiaandrade.master.nino.RegistrarNino
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.LoginUsuario
import com.example.pasantiaandrade.master.terapista.RegistroTerapista
import com.example.pasantiaandrade.master.terapista.ListadoTerapistas
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_interfaz_master.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InterfazMaster : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var habilitarEdicion : Boolean = false

    private var idUsuario: Int?= null

    private var fotoTemporal : String? = null

    private val requestImage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz_master)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        dbHelper = DBHelper(this)

        cargarTipografia()

        cargarPerfil()

        btnEnableEditMasterProfile.setOnClickListener { habilitarEdicion() }

        btnTomarFotoMasterProfile.setOnClickListener { tomarFoto() }

        btnGuardarCambiosMasterProfile.setOnClickListener{ guardarCambios() }

        btnLogOutMasterProfile.setOnClickListener { singOut() }

        btnCreateChildProfileMaster.setOnClickListener { startActivity(Intent(this@InterfazMaster, RegistrarNino::class.java).putExtra("MasterID",idUsuario)) }

        btnCreateTerapistaProfileMaster.setOnClickListener { startActivity(Intent(this@InterfazMaster, RegistroTerapista::class.java).putExtra("MasterID",idUsuario))  }

        btnListadoNinosMaster.setOnClickListener { startActivity(Intent(this@InterfazMaster, ListadoNinos::class.java))  }

        btnListadoTerapistaProfileMaster.setOnClickListener { startActivity(Intent(this@InterfazMaster, ListadoTerapistas::class.java).putExtra("MasterID",idUsuario)) }


    }

    private fun singOut() {
        val intent = Intent(this@InterfazMaster, LoginUsuario::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(Intent(intent))
    }

    private fun guardarCambios() {
        if (!habilitarEdicion){
            TastyToast.makeText(this@InterfazMaster, "habilitarEdicion DesHabilitada..", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
        else{
            val builder = AlertDialog.Builder(this@InterfazMaster)
            builder.setTitle("ConfirmarCambios....")
            builder.setMessage("Usted esta Seguro de cambiar sus datos Personales...??")
            builder.setPositiveButton("SI"){ _, _ ->
                TastyToast.makeText(this@InterfazMaster, "Datos Guardados", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                dbHelper.updateUser(Terapista(idUsuario!!,txtNombreMasterProfile.text.toString(),txtApellidoMasterProfile.text.toString(),"master",fotoTemporal!!,txtTelefonoMasterProfile.text.toString(),"patito123"))
                this.desHabilitarCampos()
                habilitarEdicion = false
            }
            builder.setNegativeButton("No"){_, _ ->
                TastyToast.makeText(this@InterfazMaster, "habilitarEdicion Cancelada", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
                this.cargarPerfil()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun tomarFoto() {
        if (!habilitarEdicion){
            TastyToast.makeText(this@InterfazMaster, "habilitarEdicion DesHabilitada..", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }else{
            TastyToast.makeText(this@InterfazMaster, "Cambiando buscarFoto", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImage)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cam = MetodosAyuda(this)
        if (requestCode == requestImage && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            fotoTemporal = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$fotoTemporal")
            Glide.with(this).load(cam.buscarFoto("JPG_$fotoTemporal")).into(imgMasterProfile)
        }
    }

    private fun habilitarEdicion() {
        habilitarEdicion = if (!habilitarEdicion){
            TastyToast.makeText(this@InterfazMaster, "habilitarEdicion Habilitada", TastyToast.LENGTH_SHORT, TastyToast.WARNING)
            this.habilitarCampos()
            true
        }else{
            TastyToast.makeText(this@InterfazMaster, "habilitarEdicion DesHabilitada", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            this.desHabilitarCampos()
            false
        }

    }

    private fun habilitarCampos(){
        txtNombreMasterProfile.isEnabled =true
        txtApellidoMasterProfile.isEnabled=true
        txtTelefonoMasterProfile.isEnabled =true
        txtPasswordMasterProfile.isEnabled = true
    }

    private fun desHabilitarCampos(){
        txtNombreMasterProfile.isEnabled = false
        txtApellidoMasterProfile.isEnabled=false
        txtTelefonoMasterProfile.isEnabled =false
        txtPasswordMasterProfile.isEnabled = false
    }

    private fun cargarPerfil() {
        val camara = MetodosAyuda(this@InterfazMaster)
        try {
            val people :Terapista? = intent.extras.getSerializable("master") as? Terapista
            txtNombreMasterProfile.text = Editable.Factory.getInstance().newEditable(people!!.nombre.toString())
            txtApellidoMasterProfile.text = Editable.Factory.getInstance().newEditable(people.apellido.toString())
            txtPasswordMasterProfile.text = Editable.Factory.getInstance().newEditable(people.password.toString())
            txtTelefonoMasterProfile.text = Editable.Factory.getInstance().newEditable(people.telefono.toString())
            idUsuario = people.id
            fotoTemporal = people.imagen.toString()
            Glide.with(this).load(camara.buscarFoto("JPG_$fotoTemporal")).into(imgMasterProfile)
        }catch (e: kotlin.KotlinNullPointerException){
            Glide.with(this).load(R.drawable.nouser).into(imgMasterProfile)
        }
    }

    private fun cargarTipografia() {
        val typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        txtNombreMasterProfile.typeface = typeface
        txtApellidoMasterProfile.typeface =typeface
        txtPasswordMasterProfile.typeface =typeface
        txtTelefonoMasterProfile.typeface =typeface
        lblHabilitarMasterProfile.typeface =typeface
        lblFotoMasterProfile.typeface =typeface
        lblGuardarMasterProfile.typeface =typeface
        lblDashMasterProfile.typeface =typeface
        lblAgregarNinoMasterProfile.typeface=typeface
        lblListadoNinoMaster.typeface = typeface
        lblAgregarTerapistaMasterProfile.typeface=typeface
        lblListadoTerapistasProfileMaster.typeface=typeface
        lblLogOutMasterProfile.typeface=typeface
    }

}
