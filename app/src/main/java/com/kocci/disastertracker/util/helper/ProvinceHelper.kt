package com.kocci.disastertracker.util.helper

import com.kocci.disastertracker.data.enums.AvailableProvince
import com.kocci.disastertracker.util.exception.ProvinceNotFoundException
import kotlin.Exception

object ProvinceHelper {
    fun getAvailableProvince(): List<String> {
        val provinceList = AvailableProvince.values()
            .map { enum ->
                convertToHumanReadableName(enum)
            }.map { provinceName ->
                capitalizeProvinceName(provinceName)
            }
        return provinceList
    }

    fun getProvinceCode(provinceName: String): String {
        return deserializeToEnums(provinceName).codes
    }

    fun convertToHumanReadableName(province: AvailableProvince): String {
        return province.name.lowercase().replace("_", " ")
    }

    fun capitalizeProvinceName(provinceName: String): String {
        return provinceName.split(" ").joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
    }

    fun deserializeToEnums(provinceName: String): AvailableProvince {
        val name = provinceName.uppercase().replace(" ", "_")
        try {
            return AvailableProvince.valueOf(name)
        } catch (e: Exception) {
            throw ProvinceNotFoundException()
        }
    }

}