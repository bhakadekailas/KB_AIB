package com.kb.weatherappaib.repository

sealed class Response<T>(val data: T? = null, val error: String? = null) {
    class Loading<T> : Response<T>()
    class Success<T>(data: T?) : Response<T>(data = data)
    class Error<T>(error: String) : Response<T>(error = error)
}