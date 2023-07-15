package i.need.drugs.todoapp.data.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import i.need.drugs.todoapp.ui.MainViewModel
import javax.inject.Inject

class NetCallback(private val connectivityManager: ConnectivityManager) :
    ConnectivityManager.NetworkCallback() {

    var onNetworkAvailable: (() -> Unit)? = null
    var onNetworkLost: (() -> Unit)? = null

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        onNetworkAvailable?.invoke()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        onNetworkLost?.invoke()

    }

    fun isOnline(): Boolean {
        val capabilities =
            connectivityManager
                .getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }
}