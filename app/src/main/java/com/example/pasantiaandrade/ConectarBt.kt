package com.example.pasantiaandrade

import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.util.*
import android.graphics.Color.parseColor
import cn.pedant.SweetAlert.SweetAlertDialog



class ConectarBt {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }

    private val context:Context

    constructor(context1: Context, direccion:String){
        context = context1
        ConnectToDevice(context1).execute()
        m_address = direccion

    }

    fun sendCommand(input: String) {
        if (m_bluetoothSocket != null) {
            try{
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        (context as Activity).finish()
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {

        private var connectSuccess: Boolean = true

        private val context: Context

        val pDialog:SweetAlertDialog

        init {
            this.context = c
            pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
            pDialog.titleText = "Loading ..."
            pDialog.setCancelable(true)
            pDialog.show()
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                m_isConnected = true
            }
            pDialog.dismiss()
        }

    }
}