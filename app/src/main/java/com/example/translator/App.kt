package com.example.translator

import android.app.Application
import com.example.translator.data.WebRepoImpl
import com.example.translator.presentation.main.MainActivityPresenterImpl
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

class App : Application() {
    lateinit var mainActivityPresenterImpl: MainActivityPresenterImpl

    override fun onCreate() {
        super.onCreate()
        instance = this

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL_LOCATIONS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.client(createOkHttpClient(interceptor))
                .build()
        }

        mainActivityPresenterImpl = MainActivityPresenterImpl(WebRepoImpl(retrofit))
    }

    companion object {
        lateinit var instance: App
            private set
    }
}