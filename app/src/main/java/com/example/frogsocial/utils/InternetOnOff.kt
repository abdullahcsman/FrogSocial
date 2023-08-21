package com.example.frogsocial.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log

abstract class InternetOnOff: BaseActivity() {
    private val internetFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    private var internetBroadcast: BroadcastReceiver? = null
    private var internetOnOffListener: InternetOnOffListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setFinishOnTouchOutside(false)
        internetOnOffListener = this as InternetOnOffListener
        checkConnectivity()
    }

    private fun checkConnectivity() {

        internetBroadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (isConnectedToInternet()) {
                    internetOnOffListener!!.onInternetChange("connected")
                } else {
                    internetOnOffListener!!.onInternetChange("disconnected")
                }
            }
        }
        this.registerReceiver(internetBroadcast, internetFilter)
    }

    fun isConnectedToInternet(): Boolean {
        try {
            val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = cm.activeNetwork ?: return false
                val actNw =
                    cm.getNetworkCapabilities(networkCapabilities) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            } else {
                val activeNetwork = cm.activeNetworkInfo
                return activeNetwork != null && activeNetwork.isConnectedOrConnecting
            }
        } catch (e: Exception) {
            Log.e("NetworkChange", e.printStackTrace().toString())
            return false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetBroadcast)
    }
}