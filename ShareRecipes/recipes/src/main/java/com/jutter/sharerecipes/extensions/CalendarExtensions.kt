package com.jutter.sharerecipes.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Парсинг календаря из строки.
 * Полезная функция, везде где идет получение календаря.
 * @param format формат даты ("dd.MM.yyyy")
 */
fun String.parseCalendarByFormat(format: String): Calendar {
    val calendar = Calendar.getInstance()
    val sdf =
            SimpleDateFormat(format, Locale.ENGLISH)
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    calendar.time = sdf.parse(this)

    return calendar
}

/**
 * Перевод даты в дрцгой формат через календарь.
 *
 * @param from формат входной даты.
 * @param to формат полученной даты.
 *
 * @return строка даты в формате to.
 */
@SuppressLint("SimpleDateFormat")
fun String.dateToFormatFromFormat(from: String, to: String): String {
    val calendar = this.parseCalendarByFormat(from)
    val sdf = SimpleDateFormat(to)
    return sdf.format(calendar.time)
}

/**
 * Устанавливает время в календаре на начало дня.
 */
fun Calendar.setDayStart(): Calendar {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
    return this
}

/**
 * Устанавливает время в календаре на конец дня.
 */
fun Calendar.setDayEnd(): Calendar {
    this.set(Calendar.HOUR_OF_DAY, 23)
    this.set(Calendar.MINUTE, 59)
    this.set(Calendar.SECOND, 59)
    this.set(Calendar.MILLISECOND, 999)
    return this
}