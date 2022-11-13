package com.kb.weatherappaib.models

data class CityDataModelItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)