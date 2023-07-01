package i.need.drugs.todoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import i.need.drugs.todoapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this) [MainViewModel::class.java]
//
        viewModel.msgSnackbar.observeForever {
            if (it != null){
                this.snackBar(it)
                viewModel.msgSnackbar.value = null
                this.setNeedUpdate(true)
            }
        }
    }
}