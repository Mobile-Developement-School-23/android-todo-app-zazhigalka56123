package i.need.drugs.todoapp.presentation

import android.app.Activity
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import i.need.drugs.todoapp.R

fun Context.snackBar(message: String) {
    val decorView = (this as Activity).window.decorView
    val view = decorView.findViewById(android.R.id.content) ?: decorView.rootView
    Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .setTextColor(getColor(R.color.label_primary))
        .setBackgroundTint(getColor(R.color.back_primary))
        .show()
}
fun Context.setRevision(revision: Int) {
    val prefs = getSharedPreferences("sp", Context.MODE_PRIVATE)
    prefs.edit().putInt("revision", revision).apply()
}
fun Context.getRevision(): Int {
    val prefs = getSharedPreferences("sp", Context.MODE_PRIVATE)
    return prefs.getInt("revision", 0) + 1
}

fun Context.setNeedUpdate(flag: Boolean){
    val prefs = getSharedPreferences("sp", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("update", flag).apply()
}
fun Context.getNeedUpdate(): Boolean {
    val prefs = getSharedPreferences("sp", Context.MODE_PRIVATE)
    return prefs.getBoolean("update", true)
}
