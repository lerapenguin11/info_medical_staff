package com.example.infomedicalstaff.utilits

import java.text.SimpleDateFormat
import java.util.*

/* Файл для хранения утилитарных функции, доступных во всем приложении */

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    return timeFormat.format(time)
}