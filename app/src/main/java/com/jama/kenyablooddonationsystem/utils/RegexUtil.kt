package com.jama.kenyablooddonationsystem.utils

class RegexUtil {

    private val bloodTypeRegex = "\\((.*?)\\)".toRegex()

    fun getUserBloodType(text: String?): String {
        if (text == null) return "AB+"
        val matchResult = bloodTypeRegex.find(text)
        return matchResult!!.groups[1]!!.value
    }

}