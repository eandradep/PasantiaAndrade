package com.example.pasantiaandrade.Master.Master

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.Model.UsuarioMT
import com.example.pasantiaandrade.Model.UsuarioNino
import com.example.pasantiaandrade.R
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_registrar_nino.*
import kotlinx.android.synthetic.main.activity_registro_master_terapista.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import java.text.SimpleDateFormat
import java.util.*

class registro_master_terapista : AppCompatActivity() {

    private var URLImagen:String? = null

    private lateinit var dbHelper: DBHelper

    private val REQUEST_IMAGE_CAPTURE = 1

    var master :Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_master_terapista)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        dbHelper = DBHelper(this)

        Tipografica()


        if(intent.extras.getInt("MasterID") == 1)
            checkBoxMaster.isEnabled=true

        btnRegistrarTerapista.setOnClickListener { ValidacionValores() }

        btnTomarFotoRegistroTerapista.setOnClickListener { Camara() }

        btnCancelarRegistroTerapista.setOnClickListener { finish() }

        checkBoxMaster.onCheckedChange { buttonView, isChecked ->
            master = master == false
        }


    }

    private fun Camara() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cam = MetodosAyuda(this)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            URLImagen = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$URLImagen")
            Glide.with(this).load(cam.Buscar_Foto("JPG_$URLImagen")).into(imgRegisterTerapista)
        }
    }

    private fun ValidacionValores() {
        if (txtRegistroTerapistaNombre.text.toString().isEmpty() and txtRegistroTerapistaApellido.text.toString().isEmpty() and txtRegistroTerapistaTelefono.text.toString().isEmpty()
                and txtRegistroTerapistaPassword.text.toString().isEmpty() and txtRegistroTerapistaPasswordC.text.toString().isEmpty())
            TastyToast.makeText(this@registro_master_terapista, "LOS DATOS DEBEN ESTAR COMPLETOS", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        else{
            if (txtRegistroTerapistaPassword.text.toString().equals(txtRegistroTerapistaPasswordC.text.toString())){
                if (URLImagen != null){
                    if (!master)
                        dbHelper.addTerapistaMaster(UsuarioMT(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"terapista",URLImagen.toString(),txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                    else
                        dbHelper.addTerapistaMaster(UsuarioMT(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"master",URLImagen.toString(),txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                    TastyToast.makeText(this@registro_master_terapista, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                    this.finish()
                }
                else{

                    val builder = AlertDialog.Builder(this@registro_master_terapista)
                    builder.setTitle("Confirmar Cambios....").setMessage("Usted esta Seguro de Registrar al Nino sin un Fotografia...??")
                    builder.setPositiveButton("SI"){ _, _ ->
                        if (!master)
                            dbHelper.addTerapistaMaster(UsuarioMT(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"terapista","none",txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                        else
                            dbHelper.addTerapistaMaster(UsuarioMT(0,txtRegistroTerapistaNombre.text.toString(),txtRegistroTerapistaApellido.text.toString(),"master","none",txtRegistroTerapistaTelefono.text.toString(),txtRegistroTerapistaPasswordC.text.toString()))
                        TastyToast.makeText(this@registro_master_terapista, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                        this.finish()
                    }
                    builder.setNegativeButton("No"){ _, _ ->
                        TastyToast.makeText(this@registro_master_terapista, "Registro Cancelado", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
                    }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                }
            }
            else
                TastyToast.makeText(this@registro_master_terapista, "LOS PASSWORD DEBEN SER IGUALES", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }

    private fun Tipografica() {
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
