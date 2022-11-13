package com.kb.weatherappaib.views

import MainViewModelFactory
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kb.weatherappaib.R
import com.kb.weatherappaib.adapters.NextDayForecastAdapter
import com.kb.weatherappaib.databinding.ActivityMainBinding
import com.kb.weatherappaib.models.MyList
import com.kb.weatherappaib.repository.Response
import com.kb.weatherappaib.utils.CustomAlertDialogs
import com.kb.weatherappaib.utils.WeatherApplication
import com.kb.weatherappaib.viewModels.MainViewModel
import kotlin.math.roundToInt

/**
 * This activity shows dashboard ui of today's and next five day's weather forecast
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var maiViewModel: MainViewModel
    private val customAlertDialogs by lazy { CustomAlertDialogs(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModels()
        attachObserver()
        setOnClickListener()
    }

    private fun initViewModels() {
        val cityRepository = (application as WeatherApplication).mainRepository
        maiViewModel =
            ViewModelProvider(this, MainViewModelFactory(cityRepository))[MainViewModel::class.java]
    }

    private fun attachObserver() {
        maiViewModel.cities.observe(this) {
            when (it) {
                is Response.Loading -> {
                    customAlertDialogs.startLoadingDialog()
                }
                is Response.Success -> {
                    it.data?.let { cities ->
                        customAlertDialogs.stopLoadingDialog()
                        if (cities.size > CONSTANT_ZERO) {
                            maiViewModel.getFiveDayForecast(
                                cities[CONSTANT_ZERO].lat,
                                cities[CONSTANT_ZERO].lon
                            )
                            binding.textViewCityName.text = cities[CONSTANT_ZERO].name
                        } else {
                            binding.constraintLayoutTodayClimate.visibility = View.GONE
                            binding.rvNextFourDaysClimate.visibility = View.GONE
                            customAlertDialogs.showInformationDialog(getString(R.string.city_not_found))
                        }
                    }
                }
                is Response.Error -> {
                    binding.constraintLayoutTodayClimate.visibility = View.GONE
                    binding.rvNextFourDaysClimate.visibility = View.GONE
                    customAlertDialogs.stopLoadingDialog()
                    customAlertDialogs.showInformationDialog(it.error)
                }
            }
        }

        maiViewModel.fiveDaysWeather.observe(this) {
            when (it) {
                is Response.Loading -> {
                    customAlertDialogs.startLoadingDialog()
                }
                is Response.Success -> {
                    it.data?.let { forecasts ->
                        customAlertDialogs.stopLoadingDialog()
                        if (forecasts.list.size > CONSTANT_ZERO) {
                            mapUi(forecasts.list)
                        } else {
                            binding.constraintLayoutTodayClimate.visibility = View.GONE
                            binding.rvNextFourDaysClimate.visibility = View.GONE
                            customAlertDialogs.showInformationDialog(getString(R.string.forecast_not_found))
                        }
                    }
                }
                is Response.Error -> {
                    binding.constraintLayoutTodayClimate.visibility = View.GONE
                    binding.rvNextFourDaysClimate.visibility = View.GONE
                    customAlertDialogs.stopLoadingDialog()
                    customAlertDialogs.showInformationDialog(it.error)
                }
            }
        }
    }

    private fun mapUi(forecasts: List<MyList>) {
        val forecast = forecasts[CONSTANT_ZERO]
        binding.textViewTemperature.text = roundedValue(forecast.main.temp)
        binding.textViewClimateType.text = forecast.weather[CONSTANT_ZERO].description
        binding.textViewHighTemperature.text =
            getString(R.string.high_temp).plus(roundedValue(forecast.main.temp_max))
        binding.textViewLowTemperature.text =
            getString(R.string.low_temp).plus(roundedValue(forecast.main.temp_min))
        binding.constraintLayoutTodayClimate.visibility = View.VISIBLE
        binding.rvNextFourDaysClimate.visibility = View.VISIBLE

        val fiveDayList = mutableListOf<MyList>()
        for (index in 0..39 step 8) {
            fiveDayList.add(forecasts[index])
        }

        binding.rvNextFourDaysClimate.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = NextDayForecastAdapter(context, fiveDayList)
        }
    }

    private fun setOnClickListener() {
        binding.imageViewSearch.setOnClickListener {
            confirmEnteredCity()
        }

        binding.editTextSearch.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                confirmEnteredCity()
                true
            } else {
                false
            }
        }
    }

    private fun confirmEnteredCity() {
        if (!binding.editTextSearch.text.isNullOrEmpty()) {
            binding.editTextSearch.clearFocus()
            maiViewModel.getCities(binding.editTextSearch.text.toString())
            val imm =
                applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.editTextSearch.windowToken, 0)
        } else {
            binding.editTextSearch.error = getString(R.string.enter_your_city)
            binding.editTextSearch.requestFocus()
        }
    }

    private fun roundedValue(givenAmount: Double): String {
        return ((givenAmount * 100.0).roundToInt() / 100.0).toString() + getString(R.string.celsius)
    }

    companion object {
        const val CONSTANT_ZERO = 0
    }
}