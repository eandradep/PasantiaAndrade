package com.example.pasantiaandrade

import android.graphics.Typeface
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import com.example.pasantiaandrade.DBHelper.DBHelper
import com.example.pasantiaandrade.Model.UsuarioNino
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_registrar_nino.*
import java.lang.NumberFormatException


class RegistrarNino : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var Foto:String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_nino)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        dbHelper = DBHelper(this)

        Tipografica()

        btnRegistrarNino.setOnClickListener { RegisterNino() }


    }


    private fun RegisterNino() {
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
                        val x = item.toInt()!! + item.toInt()!!
                    }
                    if (Foto != null){
                        dbHelper.addNino(UsuarioNino(0,txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtFechaNacimientoNino.text.toString(),Foto!!,txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString()))
                        TastyToast.makeText(this@RegistrarNino, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                        this.finish()
                    }
                    else{
                        val builder = AlertDialog.Builder(this@RegistrarNino)
                        builder.setTitle("Confirmar Cambios....").setMessage("Usted esta Seguro de Registrar al Nino sin un Fotografia...??")
                        builder.setPositiveButton("SI"){dialog, which ->
                            dbHelper.addNino(UsuarioNino(0,txtRegistroNombresNino.text.toString(),txtRegistroApellidosNino.text.toString(),txtFechaNacimientoNino.text.toString(),"none",txtTelefonoRegistroNino.text.toString(),txtDireccionRegistroNino.text.toString()))
                            TastyToast.makeText(this@RegistrarNino, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                            this.finish()
                        }
                        builder.setNegativeButton("No"){dialog, which ->
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
