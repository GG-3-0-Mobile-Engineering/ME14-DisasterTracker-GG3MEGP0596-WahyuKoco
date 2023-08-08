package com.kocci.disastertracker.util.helper

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateHelperTest{

    private val FORMAT_FROM_API = DateHelper.API_DATE_FORMAT
    private val FORMAT_BEAUTIFY = DateHelper.SHOW_DATE_FORMAT

    private lateinit var dummyDate : Date
    @Before
    fun setup(){
        val apiFormatter = SimpleDateFormat(FORMAT_FROM_API, Locale.getDefault())
        val dummyFromApi = "2023-08-08T02:05:20.254Z"
        dummyDate = apiFormatter.parse(dummyFromApi) as Date
    }

    @Test
    fun `beautify date should be formatted with FORMAT_BEAUTIFY`(){
        val actualDate = "08 Aug 2023, 02:05"
        val formatter = SimpleDateFormat(FORMAT_BEAUTIFY, Locale.getDefault())
        val beautifyDate = formatter.format(dummyDate)

        Assert.assertEquals(beautifyDate, actualDate)
    }
}