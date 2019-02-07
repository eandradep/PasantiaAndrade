package com.example.pasantiaandrade.master.nino

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.modelos.Observacion
import com.example.pasantiaandrade.modelos.Nino
import com.example.pasantiaandrade.R
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_registrar_nino.*
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegistrarNino : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var existeFoto:String? = null

    private val requestImagen = 1


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_nino)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        dbHelper = DBHelper(this)

        cargarTipografia()

        btnRegistrarNino.setOnClickListener { registerNino() }

        btnTomarFotoRegistroNino.setOnClickListener { iniciarCamara() }

        btnCancelarRegistroNino.setOnClickListener { this@RegistrarNino.finish() }

        lblFechaHoraActualRegistroNino.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
    }

    private fun iniciarCamara() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImagen)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cam = MetodosAyuda(this)
        if (requestCode == requestImagen && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            existeFoto = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$existeFoto")
            Glide.with(this).load(cam.buscarFoto("JPG_$existeFoto")).into(imgRegisterChil)
        }
    }

    private fun registerNino() {
        if(!txtAreaValoracionInicial.text.toString().isBlank()){
            if (txtRegistroNombresNino.text.toString().isBlank() or txtRegistroApellidosNino.text.toString().isBlank()
                    or txtFechaNacimientoNino.text.toString().isBlank() or txtTelefonoRegistroNino.text.toString().isBlank()
                    or txtDireccionRegistroNino.text.toString().isBlank()){
                TastyToast.makeText(this@RegistrarNino, "LOS DATOS DEBEN ESTAR COMPLETOS", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
            }
            else{
                try {
                    val sourceSplit = txtFechaNacimientoNino.text.toString().split("/")
                    if(sourceSplit.size == 3 && sourceSplit[0].length == 2 && sourceSplit[1].length == 2 && sourceSplit[2].length == 4){
                        for (item in sourceSplit){
                            item.toInt() + item.toInt()
                        }
                        if (existeFoto != null){
                            dbHelper.addNino(Nino(0,txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtFechaNacimientoNino.text.toString(),existeFoto!!,txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString()))
                            TastyToast.makeText(this@RegistrarNino, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                            dbHelper.addObservacion(Observacion(0,intent.extras.getInt("MasterID"),dbHelper.buscarNinoData(txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString(),txtFechaNacimientoNino.text.toString()),lblFechaHoraActualRegistroNino.text.toString(),txtAreaValoracionInicial.text.toString()))
                            this.finish()
                        }
                        else{
                            val builder = AlertDialog.Builder(this@RegistrarNino)
                            builder.setTitle("Confirmar Cambios....").setMessage("Usted esta Seguro de Registrar al Nino sin un Fotografia...??")
                            builder.setPositiveButton("SI"){ _, _ ->
                                dbHelper.addNino(Nino(0,txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtFechaNacimientoNino.text.toString(),"none",txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString()))
                                TastyToast.makeText(this@RegistrarNino, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                                dbHelper.addObservacion(Observacion(0,intent.extras.getInt("MasterID"),dbHelper.buscarNinoData(txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString(),txtFechaNacimientoNino.text.toString()),lblFechaHoraActualRegistroNino.text.toString(),txtAreaValoracionInicial.text.toString()))

                                this.finish()
                            }
                            builder.setNegativeButton("No"){ _, _ ->
                                TastyToast.makeText(this@RegistrarNino, "Registro Cancelado", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
                            }
                            val dialog: AlertDialog = builder.create()
                            dialog.show()
                        }
                    }

                }catch (e:NumberFormatException){
                    TastyToast.makeText(this@RegistrarNino, "Formato de Fecha Incorrecta", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
                }
            }
        }else{
            TastyToast.makeText(this@RegistrarNino, "INGRESE UNA VALORACION INICIAL", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }
    }

    private fun cargarTipografia() {
        val typeface = Typeface.createFromAsset(assets, "fonts/tipografia.otf")
        txtRegistroNombresNino.typeface=typeface
        txtRegistroApellidosNino.typeface=typeface
        txtFechaNacimientoNino.typeface=typeface
        txtTelefonoRegistroNino.typeface=typeface
        txtDireccionRegistroNino.typeface=typeface
        lblTituloValoracionInicalNino.typeface=typeface
        lblFechaHoraActualRegistroNino.typeface=typeface
        txtAreaValoracionInicial.typeface=typeface
        lblRegistrarNino.typeface=typeface
        lblCancelarRegistroNino.typeface=typeface
        lblTomarFotoRegistroNino.typeface=typeface

    }

}
