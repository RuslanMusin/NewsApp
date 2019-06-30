package com.itis.newsapp.presentation.base.databinding.livedata

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ConnectionLiveData @Inject constructor(val application: Application) : MutableLiveData<Boolean>() {

    private var broadcastReceiver: BroadcastReceiver? = null
    private var isSet = false

    fun isInternetOn(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun onActive() {
        registerBroadCastReceiver()
    }

    override fun onInactive() {
        unRegisterBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        if (broadcastReceiver == null) {
            val filter = IntentFilter()
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(_context: Context, intent: Intent) {
                    intent.extras?.let {
                        val info = it.getParcelable<NetworkInfo>("networkInfo")
                        info?.apply {
                            if (!isSet) {
                                value = isConnected
                                isSet = true
                                Log.d("TAG", "connect = $isConnected")
                                Handler().postDelayed({
                                    isSet = false
                                }, 1000)
                            }
                        }
                    }
                }
            }

            application.registerReceiver(broadcastReceiver, filter)
        }
    }

    private fun unRegisterBroadCastReceiver() {
        if (broadcastReceiver != null) {
            application.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }
    }
}