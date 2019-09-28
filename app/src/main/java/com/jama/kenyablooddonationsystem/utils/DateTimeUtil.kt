package com.jama.kenyablooddonationsystem.utils

import android.text.format.DateUtils

class DateTimeUtil {

    fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString((timestamp * 1000), now, DateUtils.MINUTE_IN_MILLIS).toString()
    }

}