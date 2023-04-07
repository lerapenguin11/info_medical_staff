package com.example.infomedicalstaff.utilits

import android.content.Context
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

/* Файл для хранения утилитарных функции, доступных во всем приложении */

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    return timeFormat.format(time)
}

fun hideKeyboard() {
    /* Функция скрывает клавиатуру */
    val imm: InputMethodManager = APP_ACTIVITY.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    imm.hideSoftInputFromWindow(APP_ACTIVITY.window.decorView.windowToken, 0)
}