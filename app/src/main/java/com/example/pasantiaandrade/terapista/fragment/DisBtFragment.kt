package com.example.pasantiaandrade.terapista.fragment

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.example.pasantiaandrade.MetodosAyuda
import com.example.pasantiaandrade.R
import com.example.pasantiaandrade.adaptador.listas.ListaDispositivos
import com.example.pasantiaandrade.dbhelper.DBHelper
import com.example.pasantiaandrade.modelos.DispositivoBluetooth
import com.example.pasantiaandrade.modelos.Terapista
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_registro_master_terapista.*
import kotlinx.android.synthetic.main.fragment_dis_bt.view.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class DispositivoBluetooth : Fragment() {

    private var v: View? =null

    var predefinido :Boolean = false


    private var mBluetoothAdapter: BluetoothAdapter? = null
    private lateinit var mPairDevices: Set<BluetoothDevice>
    private val solicitudHabilitacion = 1
    private lateinit var dbHelper: DBHelper



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_dis_bt, container, false)
        this.cargarEstilos()
        dbHelper = DBHelper(v!!.context)
        v!!.btnCancelarRegistroDispositivo.setOnClickListener { this.cambiarFragment(HomeFragment()) }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(mBluetoothAdapter == null) {
            TastyToast.makeText(v!!.context, "Este DIspositivo No Soporta Bluetooth", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
            this.cambiarFragment(HomeFragment())
        }
        if(!mBluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, solicitudHabilitacion)
        }else{
            this.pairedDeviceList()
        }

        v!!.btnRegistrarDispositivo.setOnClickListener { this.guardarDispositivo() }

        v!!.checkBoxDispPredeterminado.onCheckedChange { _, _ ->
            Log.d("Cmabiar","Valor")
            predefinido = predefinido == false
        }


        return v
    }

    private fun guardarDispositivo() {
        if (v!!.lblDispBT.text.isNullOrEmpty()){
            TastyToast.makeText(v!!.context, "Seleccione un Dispositivo de la Lista", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }else{
            val builder = AlertDialog.Builder(v!!.context)
            builder.setTitle("Confirmar Cambios....").setMessage("Usted esta Seguro de Registrar al Nino sin un Fotografia...??")
            builder.setPositiveButton("SI"){ _, _ ->
                if (!predefinido)
                    dbHelper.addDispositivo(DispositivoBluetooth(v!!.lblDispBT.text.toString(),v!!.txtNombreDispositivo.text.toString(),"noPredefinido"))
                else
                    dbHelper.addDispositivo(DispositivoBluetooth(v!!.lblDispBT.text.toString(),v!!.txtNombreDispositivo.text.toString(),"Predefinido"))
                TastyToast.makeText(v!!.context, "Registro Creado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
            }
            builder.setNegativeButton("No"){ _, _ ->
                TastyToast.makeText(v!!.context, "Registro Cancelado", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }


    private fun pairedDeviceList() {
        mPairDevices = mBluetoothAdapter!!.bondedDevices
        val lstDispositivos = ArrayList<DispositivoBluetooth>()
        if (!mPairDevices.isEmpty()) {
            for (device: BluetoothDevice in mPairDevices) {
                val dispositivo:DispositivoBluetooth = dbHelper.buscarDispositivoId(device.address.toString())
                if (dispositivo.dispositivoDireccion == null){
                    dispositivo.dispositivoDireccion = device.address
                    dispositivo.dispositivoNombre = device.name
                    dispositivo.dispositivoPredefinido = "noPredefinido"
                }else
                    v!!.btnRegistrarDispositivo.isEnabled = false

                Log.d("dispositivo","${dispositivo.dispositivoPredefinido}")
                lstDispositivos.add(dispositivo)

            }
        } else {
            TastyToast.makeText(v!!.context, "No tiene DIspositivos Emparejados", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
        }

        v!!.select_device_list.adapter = ListaDispositivos(v!!.context,lstDispositivos)
        v!!.select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            v!!.lblDispBT.text = lstDispositivos[position].dispositivoDireccion.toString()
            v!!.txtNombreDispositivo.text = Editable.Factory.getInstance().newEditable(lstDispositivos[position].dispositivoNombre.toString())
            v!!.checkBoxDispPredeterminado.isChecked = lstDispositivos[position].dispositivoPredefinido.toString() == "Predefinido"
            this.predefinido = lstDispositivos[position].dispositivoPredefinido.toString() == "Predefinido"
        }
    }

    private fun cambiarFragment(fragment: Fragment){
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.RelativeLayout, MetodosAyuda(activity!!).addBundleFragment(fragment,(arguments!!.getSerializable("Terapista") as Terapista?)!!,"Terapista")).remove(this).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == solicitudHabilitacion) {
            if (resultCode == Activity.RESULT_OK) {
                if (mBluetoothAdapter!!.isEnabled) {
                    TastyToast.makeText(v!!.context, "Dispositivo Bluettoth Activado", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS)
                } else {
                    TastyToast.makeText(v!!.context, "Dispositivo Bluettoth DesActivado", TastyToast.LENGTH_SHORT, TastyToast.ERROR)
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                TastyToast.makeText(v!!.context, "Conexion Fallida", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING)
            }
        }
        this.pairedDeviceList()
    }

    private fun cargarEstilos(){
        val typeface = Typeface.createFromAsset(activity!!.assets, "fonts/tipografia.otf")
        v!!.checkBoxDispPredeterminado.typeface = typeface
        v!!.lblDispBT.typeface= typeface
        v!!.txtNombreDispositivo.typeface= typeface
        v!!.lblRegistrarDispositivo.typeface=typeface
        v!!.lblCancelarRegistroDispositivo.typeface=typeface
    }

}
