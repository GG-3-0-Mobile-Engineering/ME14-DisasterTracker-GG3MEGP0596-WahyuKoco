package com.kocci.disastertracker.util.helper

import com.kocci.disastertracker.util.exception.ProvinceNotFoundException
import org.junit.Assert
import org.junit.Test

class ProvinceHelperTest {
    //test name should have a pattern -> important
    //business logic should be tested by behaviour
    @Test
    fun `getProvinceCode, if not found, should throw ProvinceNotFound Exception`() {
        Assert.assertThrows(ProvinceNotFoundException::class.java) {
            ProvinceHelper.getProvinceCode(
                "Filipino"
            )
        }
    }

    @Test
    fun `getAvailableProvince, when invoked, should return a list of uppercase strings`() {
        val provinceList = ProvinceHelper.getAvailableProvince()
        val provinceName = provinceList.findLast { it.contains(" ") } ?: provinceList[0]

        if (provinceName.contains(" ")) { //Sumatera Utara, Bangka Belitung, dkk
            provinceName.split(" ").forEach { name ->
                val expected = name.replaceFirstChar { it.uppercase() }
                Assert.assertEquals(name, expected)
            }
        } else { //Jakarta, Banten, dkk
            val expected = provinceName.replaceFirstChar { it.uppercase() }
            Assert.assertEquals(provinceName, expected)
        }
    }

    @Test
    fun `getAvailableProvince, when invoked, should not contain _`() {
        val provinceList = ProvinceHelper.getAvailableProvince()
        val provinceName = provinceList.findLast { it.contains(" ") } ?: provinceList[0]
        Assert.assertEquals(provinceName.contains("_"), false)
    }


}