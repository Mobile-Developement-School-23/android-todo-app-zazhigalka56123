package i.need.drugs.todoapp

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import i.need.drugs.todoapp.di.component.DaggerApplicationComponent
import i.need.drugs.todoapp.domain.Constant.SP_REVISION
import i.need.drugs.todoapp.domain.Constant.SP_THEME
import i.need.drugs.todoapp.domain.Constant.THEME_SYSTEM

class TodoApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    companion object{
        fun revision(sp: SharedPreferences) = sp.getInt(SP_REVISION, 0)
        fun setRevision(rev: Int, sp: SharedPreferences) = sp.edit().putInt(SP_REVISION, rev).apply()

        fun theme(sp: SharedPreferences) = sp.getInt(SP_THEME, THEME_SYSTEM)
        fun setTheme(theme: Int, sp: SharedPreferences) = sp.edit().putInt(SP_THEME, theme).apply()

        fun snackBar(view: View, text: String, flag: Boolean?, color: Int = 1) {
            if (flag == true) {
                Log.d("tttet", text)
                val c = if (color == 1) R.color.red else R.color.green
                Snackbar.make(view, text, 2000)
                    .setBackgroundTint(view.context.getColor(c))
                    .setText(text)
                    .setTextColor(view.context.getColor(R.color.label_primary))
                    .show()
            }
        }
    }
}