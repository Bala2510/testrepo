package com.application.pepul.util

import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.application.pepul.R
import com.application.pepul.app.App
import com.application.pepul.ui.activity.MainActivity


import com.google.gson.Gson
import com.google.gson.GsonBuilder

import java.io.UnsupportedEncodingException
import java.math.BigDecimal
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.Throws

object UtilsDefault {

    fun isOnline(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val netInfo = cm.activeNetworkInfo
                if (netInfo != null) {
                    return (netInfo.isConnected() && (netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                val netInfo = cm.activeNetwork
                if (netInfo != null) {
                    val nc = cm.getNetworkCapabilities(netInfo);

                    return (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc!!.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ));
                }
            }
        }

        return haveConnectedWifi || haveConnectedMobile
    }
}