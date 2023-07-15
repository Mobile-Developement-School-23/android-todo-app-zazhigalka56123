package i.need.drugs.todoapp.data.remote

import android.content.SharedPreferences
import i.need.drugs.todoapp.TodoApp.Companion.revision
import i.need.drugs.todoapp.domain.Constant.REVISION_HEADER
import i.need.drugs.todoapp.domain.Constant.TOKEN
import i.need.drugs.todoapp.domain.Constant.TOKEN_HEADER
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class Interceptor @Inject constructor(private val sp: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val newReq = request.newBuilder()
            .header(TOKEN_HEADER, "Bearer $TOKEN")
            .header(REVISION_HEADER, revision(sp).toString()).build()
        return chain.proceed(newReq)
    }
}