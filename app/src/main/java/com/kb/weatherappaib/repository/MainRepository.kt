package com.kb.weatherappaib.repository

import RetrofitService
import RetrofitHelper
import android.content.Context
import com.kb.weatherappaib.R
import com.kb.weatherappaib.models.CityDataModel
import com.kb.weatherappaib.models.FiveDayForecast
import com.kb.weatherappaib.utils.NetworkUtils

class MainRepository(
    private val retrofitService: RetrofitService,
    private val applicationContext: Context
) {

    suspend fun getCities(enteredCity: String): Response<CityDataModel> {
        return if (NetworkUtils.isOnline(applicationContext)) {
            try {
                val result = retrofitService.getCity(enteredCity, RetrofitHelper.API_KEY)
                if (result.body() != null) {
                    Response.Success(result.body())
                } else {
                    Response.Error(applicationContext.resources.getString(R.string.api_error))
                }
            } catch (e: Exception) {
                Response.Error(e.message.toString())
            }
        } else {
            Response.Error(applicationContext.resources.getString(R.string.network_not_found))
        }
    }

    suspend fun getFiveDayForecast(lat: Double, lon: Double): Response<FiveDayForecast> {
        return if (NetworkUtils.isOnline(applicationContext)) {
            try {
                val result = retrofitService.getFiveDayForecast(
                    lat,
                    lon,
                    RetrofitHelper.API_KEY,
                    RetrofitHelper.UNIT
                )
                if (result.body() != null) {
                    Response.Success(result.body())
                } else {
                    Response.Error(applicationContext.resources.getString(R.string.api_error))
                }
            } catch (e: Exception) {
                Response.Error(e.message.toString())
            }
        } else {
            Response.Error(applicationContext.resources.getString(R.string.network_not_found))
        }
    }
}