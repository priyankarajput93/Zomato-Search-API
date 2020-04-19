package com.project.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.annotation.StringRes
import com.project.base.BaseApp

/**
 * Created by Priyanka on 16/04/2020
 */
object CommonUtils{

    var context = BaseApp.getContext()

    fun isNetworkAvailable(): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo
        if (info != null) {
            return info.state == NetworkInfo.State.CONNECTED || info.state == NetworkInfo.State.CONNECTING
        }
        return false
    }

    fun toast(@StringRes message: Int) {
        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}