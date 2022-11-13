package com.kb.weatherappaib.models

data class FiveDayForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<MyList>,
    val message: Int
)