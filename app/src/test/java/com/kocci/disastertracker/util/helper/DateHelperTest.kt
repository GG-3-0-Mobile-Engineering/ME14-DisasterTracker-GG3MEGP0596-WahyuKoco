package com.kocci.disastertracker.util.helper

import org.junit.Assert
import org.junit.Test

class DateHelperTest{

    @Test
    fun `beautifyDate, when invoked, should return beautified date`(){
        //change the name function to convinience test method
        val expectedDate = "08 Aug 2023, 02:05"
        val input = "2023-08-08T02:05:20.254Z"
        val result = DateHelper.beautifyDate(input)

        Assert.assertEquals(result, expectedDate)
    }
}