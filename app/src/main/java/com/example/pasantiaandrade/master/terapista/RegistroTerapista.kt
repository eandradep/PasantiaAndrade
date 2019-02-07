package com.example.pasantiaandrade.master.terapista

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.modelos.Terapista
import com.example.pasantiaandrade.R
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_registro_master_terapista.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegistroTerapista : AppCompatActivity() {

    private var urlImagen:String? = null

    private lateinit var dbHelper: DBHelper

    private val requestImage = 1

    var master :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_master_terapista)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        dbHelper = DBHelper(this)

        iniciarTipografia()


        if(intent.extras.getInt("MasterID") == 1)
            checkBoxMaster.isEnabled=true

        btnRegistrarTerapista.setOnClickListener { validacionValores() }

        btnTomarFotoRegistroTerapista.setOnClickListener { iniciarCamara() }

        btnCancelarRegistroTerapista.setOnClickListener { finish() }

        checkBoxMaster.onCheckedChange { _, _ ->
            master = master == false
        }


    }

    private fun iniciarCamara() {
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
            urlImagen = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$urlImagen")
            Glide.with(this).load(cam.buscarFoto("JPG_$urlImagen")).into(imgRegisterTerapista)
        }
    }

    private fun validacionValores() {
        if (txtRegistroTerapistaNombre.text.toString().isEmpty() and txtRegistroTerapistaApellido.text.toString().isEmpty() and txtRegistroTerapistaTelefono.text.toString().isEmpty()
                and txtRegistroTerapistaPassword.text.toString().isEmpty() and txtRegistroTerapistaPasswordC.text.toString().isEmpty())
            TastyToast.makeText(this@RegistroTerapista, "LOS DATOS DEBEN ESTAR COMPLETOS", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        else{
            if (txtRegistroTerapistaPassword.text.toString() == txtRegistroTerapistaPasswordC.text.toString()){
                if (null != urlImagen){
                    if (!master)
                        dbHelper.addTerapistaMaster(Terapista(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"terapista",urlImagen.toString(),txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                    else
                        dbHelper.addTerapistaMaster(Terapista(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"master",urlImagen.toString(),txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                    TastyToast.makeText(this@RegistroTerapista, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                    this.finish()
                }
                else{

                    val builder = AlertDialog.Builder(this@RegistroTerapista)
                    builder.setTitle("Confirmar Cambios....").setMessage("Usted esta Seguro de Registrar al Nino sin un Fotografia...??")
                    builder.setPositiveButton("SI"){ _, _ ->
                        if (!master)
                            dbHelper.addTerapistaMaster(Terapista(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"terapista","none",txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                        else
                            dbHelper.addTerapistaMaster(Terapista(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"master","none",txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                        TastyToast.makeText(this@RegistroTerapista, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                        this.finish()
                    }
                    builder.setNegativeButton("No"){ _, _ ->
                        TastyToast.makeText(this@RegistroTerapista, "Registro Cancelado", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
            else
                TastyToast.makeText(this@RegistroTerapista, "LOS PASSWORD DEBEN SER IGUALES", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }

    private fun iniciarTipografia() {
        val typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        txtRegistroTerapistaNombre.typeface=typeface
        txtRegistroTerapistaApellido.typeface=typeface
        txtRegistroTerapistaTelefono.typeface=typeface
        txtRegistroTerapistaPassword.typeface=typeface
        txtRegistroTerapistaPasswordC.typeface=typeface
        lblRegistroTerapistaPassword.typeface=typeface
        lblRegistrarTerapista.typeface=typeface
        lblTomarFotoRegistroTerapista.typeface=typeface
        lblCancelarRegistroTerapista.typeface=typeface
        checkBoxMaster.typeface=typeface
    }
}
