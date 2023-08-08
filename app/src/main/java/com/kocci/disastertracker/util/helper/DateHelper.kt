package com.kocci.disastertracker.util.helper

import java.text.SimpleDateFormat
import java.util.Locale

object DateHelper {

    const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val SHOW_DATE_FORMAT = "dd MMM yyyy, HH:mm"

    fun beautifyDate(rawDate: String): String {
        val apiFormat = SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault())
        val date = apiFormat.parse(rawDate)
        val newFormat = SimpleDateFormat(SHOW_DATE_FORMAT, Locale.getDefault())

        return newFormat.format(date)
    }
}