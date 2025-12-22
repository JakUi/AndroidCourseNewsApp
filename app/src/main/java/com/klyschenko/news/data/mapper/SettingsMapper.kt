package com.klyschenko.news.data.mapper

import com.klyschenko.news.domain.entity.Interval
import com.klyschenko.news.domain.entity.Language
import com.klyschenko.news.domain.entity.RefreshConfig
import com.klyschenko.news.domain.entity.Settings

fun Settings.toRefreshConfig(): RefreshConfig {

    return RefreshConfig(language, interval, wifiOnly)
}

fun String.toLanguage(): Language {
    val language = when(this) {
        "English" -> Language.ENGLISH
        "Русский" -> Language.RUSSIAN
        "Français" -> Language.FRENCH
        "Deutsch" -> Language.GERMAN
        else -> error("Unknown language: $this")
    }
    return language
}

fun String.toMinutes(): Int {
    val minutes = when(this) {
        "15 minutes" -> 15
        "30 minutes" -> 30
        "1 hour" -> 60
        "2 hours" -> 120
        "8 hours" -> 480
        "24 hours" -> 1440
        else -> error("Unknown interval: $this")
    }
    return minutes
}

fun Int.toIntervalDropdown(): String {
    val interval = when(this) {
        Interval.MIN_15.minutes -> "15 minutes"
        Interval.MIN_30.minutes -> "30 minutes"
        Interval.HOUR_1.minutes -> "1 hour"
        Interval.HOUR_2.minutes -> "2 hours"
        Interval.HOUR_8.minutes -> "8 hours"
        Interval.HOUR_24.minutes -> "24 hours"
        else -> error("Unknown interval: $this")
    }
    return interval
}
