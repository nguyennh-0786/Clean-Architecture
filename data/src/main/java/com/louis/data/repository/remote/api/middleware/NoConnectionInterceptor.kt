package com.louis.data.repository.remote.api.middleware

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.louis.data.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoConnectionInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) {
            throw NoConnectivityException(context)
        } else if (!isInternetAvailable()) {
            throw NoInternetException(context)
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val connection = connectivityManager.getNetworkCapabilities(network)
            return connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
            }
            return false
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val socket = Socket()
            val socketAddress = InetSocketAddress(HOST_NAME, PORT)

            socket.connect(socketAddress, TIME_OUT)
            socket.close()

            true
        } catch (e: IOException) {
            false
        }

    }

    class NoConnectivityException(private val context: Context) : IOException() {
        override val message: String
            get() = context.resources.getString(R.string.no_network_available)
    }

    class NoInternetException(private val context: Context) : IOException() {
        override val message: String
            get() = context.resources.getString(R.string.no_internet_available)
    }

    companion object {
        const val HOST_NAME = "8.8.8.8"
        const val PORT = 53
        const val TIME_OUT = 1500
    }
}