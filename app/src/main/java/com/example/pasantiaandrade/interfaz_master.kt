package com.example.pasantiaandrade

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
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioMT
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_interfaz_master.*
import java.text.SimpleDateFormat
import java.util.*

class interfaz_master : AppCompatActivity() {

    private lateinit var db: DBHelper

    private var Edicion : Boolean = false

    private var ID: Int?= null

    private var FotoTemporal : String? = null

    private val REQUEST_IMAGE_CAPTURE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interfaz_master)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        db = DBHelper(this)

        Tipografica()

        Perfil()

        btnEnableEditMasterProfile.setOnClickListener { HabilitarEdicion() }

        btnTomarFotoMasterProfile.setOnClickListener { TomarFoto() }

        btnGuardarCambiosMasterProfile.setOnClickListener{ GuardarCambios() }

        btnLogOutMasterProfile.setOnClickListener { SingOut() }

        btnCreateChildProfileMaster.setOnClickListener { startActivity(Intent(this@interfaz_master, RegistrarNino::class.java).putExtra("MasterID",ID)) }

        btnListadoNinosMaster.setOnClickListener { startActivity(Intent(this@interfaz_master, Listado_Ninos::class.java))  }


    }

    private fun SingOut() {
        val intent = Intent(this@interfaz_master, seleccion_usuarios::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(Intent(intent))
    }

    private fun GuardarCambios() {
        if (Edicion == false){
            TastyToast.makeText(this@interfaz_master, "Edicion DesHabilitada..", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
        else{
            val builder = AlertDialog.Builder(this@interfaz_master)
            builder.setTitle("ConfirmarCambios....")
            builder.setMessage("Usted esta Seguro de cambiar sus datos Personales...??")
            builder.setPositiveButton("SI"){dialog, which ->
                TastyToast.makeText(this@interfaz_master, "Datos Guardados", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                db.updateUser(UsuarioMT(ID!!,txtNombreMasterProfile.text.toString(),txtApellidoMasterProfile.text.toString(),"master",FotoTemporal!!,txtTelefonoMasterProfile.text.toString(),"patito123"))
                this.DesHabilitarCampos()
                Edicion = false
            }
            builder.setNegativeButton("No"){dialog, which ->
                TastyToast.makeText(this@interfaz_master, "Edicion Cancelada", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
                this.Perfil()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun TomarFoto() {
        if (Edicion == false){
            TastyToast.makeText(this@interfaz_master, "Edicion DesHabilitada..", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }else{
            TastyToast.makeText(this@interfaz_master, "Cambiando Buscar_Foto", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cam = Camera(this)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            FotoTemporal = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$FotoTemporal")
            Glide.with(this).load(cam.Buscar_Foto("JPG_$FotoTemporal")).into(imgMasterProfile)
        }
    }

    private fun HabilitarEdicion() {
        if (!Edicion){
            TastyToast.makeText(this@interfaz_master, "Edicion Habilitada", TastyToast.LENGTH_SHORT, TastyToast.WARNING)
            this.HabilitarCampos()
            Edicion = true
        }else{
            TastyToast.makeText(this@interfaz_master, "Edicion DesHabilitada", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            this.DesHabilitarCampos()
            Edicion =false
        }

    }

    private fun HabilitarCampos(){
        txtNombreMasterProfile.isEnabled =true
        txtApellidoMasterProfile.isEnabled=true
        txtTelefonoMasterProfile.isEnabled =true
        txtPasswordMasterProfile.isEnabled = true
    }

    private fun DesHabilitarCampos(){
        txtNombreMasterProfile.isEnabled = false
        txtApellidoMasterProfile.isEnabled=false
        txtTelefonoMasterProfile.isEnabled =false
        txtPasswordMasterProfile.isEnabled = false
    }

    private fun Perfil() {
        val camara = Camera(this@interfaz_master)
        try {
            val people :UsuarioMT? = intent.extras.getSerializable("master") as? UsuarioMT
            txtNombreMasterProfile.text = Editable.Factory.getInstance().newEditable(people!!.TM_Nombre.toString())
            txtApellidoMasterProfile.text = Editable.Factory.getInstance().newEditable(people.TM_Apellido.toString())
            txtPasswordMasterProfile.text = Editable.Factory.getInstance().newEditable(people.TM_Password.toString())
            txtTelefonoMasterProfile.text = Editable.Factory.getInstance().newEditable(people.TM_Telefono.toString())
            ID = people.TM_Id
            FotoTemporal = people.TM_Imagen.toString()
            Glide.with(this).load(camara.Buscar_Foto("JPG_$FotoTemporal")).into(imgMasterProfile)
        }catch (e: kotlin.KotlinNullPointerException){
            Glide.with(this).load(R.drawable.nouser).into(imgMasterProfile)
        }
    }

    private fun Tipografica() {
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
    }

}
