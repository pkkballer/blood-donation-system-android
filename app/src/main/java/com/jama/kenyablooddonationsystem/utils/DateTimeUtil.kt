package com.jama.kenyablooddonationsystem.utils

import android.annotation.SuppressLint
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*


class DateTimeUtil {

    fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString((timestamp * 1000), now, DateUtils.MINUTE_IN_MILLIS).toString()
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(timestamp: Long): String {
        val formatter = SimpleDateFormat("d, MMM y")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp * 1000
        return formatter.format(calendar.time)
    }

    fun getDateNow(): String {
        val formatter = SimpleDateFormat("d, MMM y")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        return formatter.format(calendar.time)
    }

}