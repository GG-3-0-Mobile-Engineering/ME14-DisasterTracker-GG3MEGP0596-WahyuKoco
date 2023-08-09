package com.kocci.disastertracker.util.helper

import com.kocci.disastertracker.util.exception.ProvinceNotFoundException
import org.junit.Assert
import org.junit.Test

class ProvinceHelperTest {
    @Test
    fun `if the province not found, should throw ProvinceNotFound Exception`() {
        Assert.assertThrows(ProvinceNotFoundException::class.java) {
            ProvinceHelper.getProvinceCode(
                "Filipino"
            )
        }
    }

    @Test
    fun `provided province should be human readable`() {
        val provinceList = ProvinceHelper.getAvailableProvince()
        val provinceName = provinceList.findLast { it.contains(" ") } ?: provinceList[0]
//        val provinceName = "JAKARTA"


        if (provinceName.contains(" ")) { //Sumatera Utara, Bangka Belitung, dkk
            provinceName.split(" ").map { name ->
                val expected = name.replaceFirstChar { it.uppercase() }
                Assert.assertEquals(name, expected)
            }
        } else { //Jakarta, Banten, dkk
            val expected = provinceName.replaceFirstChar { it.uppercase() }
            Assert.assertEquals(provinceName, expected)
        }

        if (provinceName.contains("_")) {
            //I want to assert if name does not contain "_" but i can't find a proper solution
            Assert.assertTrue(false)
        }

    }
}