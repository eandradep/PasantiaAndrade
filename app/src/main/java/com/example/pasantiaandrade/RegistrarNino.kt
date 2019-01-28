package com.example.pasantiaandrade

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
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.Tab_Observacion
import com.example.pasantiaandrade.Model.UsuarioNino
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_registrar_nino.*
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RegistrarNino : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var Foto:String? = null

    private val REQUEST_IMAGE_CAPTURE = 1


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_nino)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        dbHelper = DBHelper(this)

        Tipografica()

        btnRegistrarNino.setOnClickListener { RegisterNino() }

        btnTomarFotoRegistroNino.setOnClickListener { Camara() }

        btnCancelarRegistroNino.setOnClickListener { this@RegistrarNino.finish() }

        lblFechaHoraActualRegistroNino.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
    }

    private fun Camara() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val cam = Camera(this)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras.get("data") as Bitmap
            Foto = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            cam.bitmapToFile(imageBitmap,"JPG_$Foto")
            Glide.with(this).load(cam.Buscar_Foto("JPG_$Foto")).into(imgRegisterChil)
        }
    }

    private fun RegisterNino() {
        if(!txtAreaValoracionInicial.text.toString().isNullOrBlank()){
            if (txtRegistroNombresNino.text.toString().isNullOrBlank() or txtRegistroApellidosNino.text.toString().isNullOrBlank()
                    or txtFechaNacimientoNino.text.toString().isNullOrBlank() or txtTelefonoRegistroNino.text.toString().isNullOrBlank()
                    or txtDireccionRegistroNino.text.toString().isNullOrBlank()){
                TastyToast.makeText(this@RegistrarNino, "LOS DATOS DEBEN ESTAR COMPLETOS", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
            }
            else{
                try {
                    val sourceSplit = txtFechaNacimientoNino.text.toString().split("/")
                    if(sourceSplit.size == 3 && sourceSplit[0].length == 2 && sourceSplit[1].length == 2 && sourceSplit[2].length == 4){
                        for (item in sourceSplit){
                            val x = item.toInt() + item.toInt()
                        }
                        if (Foto != null){
                            dbHelper.addNino(UsuarioNino(0,txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtFechaNacimientoNino.text.toString(),Foto!!,txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString()))
                            TastyToast.makeText(this@RegistrarNino, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                            dbHelper.RegistrarValoracion(Tab_Observacion(0,intent.extras.getInt("MasterID"),dbHelper.BuscarNinoData(txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString(),txtFechaNacimientoNino.text.toString()),lblFechaHoraActualRegistroNino.text.toString(),txtAreaValoracionInicial.text.toString()))
                            this.finish()
                        }
                        else{
                            val builder = AlertDialog.Builder(this@RegistrarNino)
                            builder.setTitle("Confirmar Cambios....").setMessage("Usted esta Seguro de Registrar al Nino sin un Fotografia...??")
                            builder.setPositiveButton("SI"){ _, _ ->
                                dbHelper.addNino(UsuarioNino(0,txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtFechaNacimientoNino.text.toString(),"none",txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString()))
                                TastyToast.makeText(this@RegistrarNino, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                                dbHelper.RegistrarValoracion(Tab_Observacion(0,intent.extras.getInt("MasterID"),dbHelper.BuscarNinoData(txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString(),txtFechaNacimientoNino.text.toString()),lblFechaHoraActualRegistroNino.text.toString(),txtAreaValoracionInicial.text.toString()))

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

    private fun Tipografica() {
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
