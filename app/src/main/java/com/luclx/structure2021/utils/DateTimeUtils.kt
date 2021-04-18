package com.luclx.structure2021.utils

import java.text.SimpleDateFormat
import java.util.*

fun getTimeInISO8601(): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 1)
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(calendar.time)
}

fun getTimeInISO8601NextYear(): String {
    // add more 1 year
    val calendar = Calendar.getInstance().apply {
        add(Calendar.YEAR, 1)
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(calendar.time)
}

fun getTimeInISO8601NextWeek(): String {
    // add more 1 week
    val calendar = Calendar.getInstance().apply {
        add(Calendar.DATE, 7)
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(calendar.time)
}

fun Calendar.getTime8601(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(time)
}