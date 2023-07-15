package i.need.drugs.todoapp.data.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import i.need.drugs.todoapp.ui.MainViewModel

class NetCallback(private val viewModel: MainViewModel) :
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

    private fun isOnline(): Boolean {
        val capabilities =
            viewModel.connectManager
                .getNetworkCapabilities(viewModel.connectManager.activeNetwork)
        if (capabilities != null) {
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        }
        return false
    }
}