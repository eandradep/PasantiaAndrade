package com.example.pasantiaandrade

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.widget.Toast
import android.widget.ArrayAdapter

class SeleccionDispositivoBuetooth : AppCompatActivity() {

    private var myBluetooth: BluetoothAdapter? = null
    private var dispVinculados: Set<BluetoothDevice>? = null
    private var extraAddres = "device_address"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_dispositivo_buetooth)

        myBluetooth = BluetoothAdapter.getDefaultAdapter()

        if (myBluetooth == null) {
            Toast.makeText(applicationContext, "Bluetooth no disponible", Toast.LENGTH_LONG).show()
            finish()
        } else if (!myBluetooth!!.isEnabled) {
            val turnBTon = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnBTon, 1)
        }

    }

    private fun listaDispositivosVinculados(){
        dispVinculados = myBluetooth!!.bondedDevices
        val list :ArrayList<String>? = null

        if ((dispVinculados as MutableSet<BluetoothDevice>?)!!.size > 0) {
            for (bt in (dispVinculados as MutableSet<BluetoothDevice>?)!!) {
                list!!.add(bt.name + "\n" + bt.address)
            }
        } else {
            Toast.makeText(applicationContext, "No se han encontrado dispositivos vinculados", Toast.LENGTH_LONG).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        //listaDispositivos.setAdapter(adapter)
        //listaDispositivos.setOnItemClickListener(myListClickListener)
    }
}
