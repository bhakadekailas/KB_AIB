package com.kb.weatherappaib.utils

import RetrofitService
import android.app.Application
import com.kb.weatherappaib.repository.MainRepository

/**
 * This class is an application class to initialize required object of application level
 */
class WeatherApplication : Application() {
    lateinit var mainRepository: MainRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val retrofitService = RetrofitHelper.getInstance().create(RetrofitService::class.java)
        mainRepository = MainRepository(retrofitService, applicationContext)
    }
}