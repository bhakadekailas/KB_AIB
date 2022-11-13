package com.kb.weatherappaib.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kb.weatherappaib.models.CityDataModel
import com.kb.weatherappaib.models.FiveDayForecast
import com.kb.weatherappaib.repository.MainRepository
import com.kb.weatherappaib.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _cities = MutableLiveData<Response<CityDataModel>>()
    val cities: LiveData<Response<CityDataModel>>
        get() = _cities

    private val _fiveDaysWeather = MutableLiveData<Response<FiveDayForecast>>()
    val fiveDaysWeather: LiveData<Response<FiveDayForecast>>
        get() = _fiveDaysWeather

    fun getCities(enteredCity: String) {
        _cities.postValue(Response.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            _cities.postValue(repository.getCities(enteredCity))
        }
    }

    fun getFiveDayForecast(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            _fiveDaysWeather.postValue(repository.getFiveDayForecast(lat, lon))
        }
    }
}