package com.kb.weatherappaib.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        fun getDayFromDate(inputDate: String): String {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = inputDateFormat.parse(inputDate)
            val outputDateFormat = SimpleDateFormat("EEEE")
            return outputDateFormat.format(date as Date)
        }
    }
}