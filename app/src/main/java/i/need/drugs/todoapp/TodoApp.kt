package i.need.drugs.todoapp

import android.app.Application
import android.content.SharedPreferences
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
    }
}