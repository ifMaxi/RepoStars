package com.maxidev.repostars.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toFormattedDate(): String {
    val zonedDateTime = ZonedDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

    return formatter.format(zonedDateTime)
}