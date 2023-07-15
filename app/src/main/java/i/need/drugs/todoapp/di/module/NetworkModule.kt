package i.need.drugs.todoapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import i.need.drugs.todoapp.data.remote.Interceptor
import i.need.drugs.todoapp.data.remote.TodoApi
import i.need.drugs.todoapp.di.ApplicationScope
import i.need.drugs.todoapp.domain.Constant.SP_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
interface NetworkModule {
    companion object {
        @Provides
        @ApplicationScope
        fun provideApiUrl(): String = "https://beta.mrdekk.ru/todobackend/"

        @Provides
        @ApplicationScope
        fun provideNetworkClient(context: Context): OkHttpClient {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            return OkHttpClient().newBuilder().callTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(Interceptor(sp)).addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
        }


        @Provides
        @ApplicationScope
        fun provideRetrofit(
            url: String,
            client: OkHttpClient
        ): Retrofit = Retrofit.Builder().baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @ApplicationScope
        fun provideApi(retrofit: Retrofit): TodoApi = retrofit.create(TodoApi::class.java)
    }
}