package i.need.drugs.todoapp.ui

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import i.need.drugs.todoapp.R
import i.need.drugs.todoapp.data.util.NetCallback
import i.need.drugs.todoapp.di.component.DaggerApplicationComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    companion object {
        var isOnline: MutableLiveData<Boolean?> = MutableLiveData()
        var oldValue = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerApplicationComponent.factory().create(this).inject(this)

        val netCallback = NetCallback(connectivityManager)

        connectivityManager.registerDefaultNetworkCallback(netCallback)
        netCallback.onNetworkAvailable = {
            if (isOnline.value != true) {
                oldValue = isOnline.value ?: false
                isOnline.postValue(true)
            }
            oldValue = isOnline.value ?: false
        }
        netCallback.onNetworkLost = {
            if (isOnline.value != false) {
                oldValue = isOnline.value ?: true
                isOnline.postValue(false)
            }
        }

    }
}