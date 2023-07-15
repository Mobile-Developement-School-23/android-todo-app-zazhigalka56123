package i.need.drugs.todoapp.di.module

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import i.need.drugs.todoapp.di.ApplicationScope
import i.need.drugs.todoapp.domain.Constant.SP_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
class AppModule {

    @Provides
    @ApplicationScope
    fun provideSharedPreferences(context:Context): SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    @Provides
    @ApplicationScope
    fun provideConnectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @ApplicationScope
    fun provideScope(): CoroutineScope = CoroutineScope(SupervisorJob())
}