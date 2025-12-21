package com.klyschenko.news.data.mapper

import com.klyschenko.news.data.local.ArticleDBModel
import com.klyschenko.news.data.remote.NewsResponseDto
import com.klyschenko.news.domain.entity.Article
import com.klyschenko.news.domain.entity.Interval
import com.klyschenko.news.domain.entity.Language
import java.text.SimpleDateFormat
import java.util.Locale

fun NewsResponseDto.toDbModels(topic: String): List<ArticleDBModel> {
    return articles.map {
        ArticleDBModel(
            title = it.title,
            description = it.description,
            url = it.url,
            imageUrl = it.urlToImage,
            sourceName = it.source.name,
            topic = topic,
            publishedAt = it.publishedAt.toTimestamp()
        )
    }
}

fun Int.toInterval(): Interval {
    return Interval.entries.first { it.minutes == this }
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

fun Language.toQueryParam(): String {
    return when (this) {
        Language.ENGLISH -> "en"
        Language.RUSSIAN -> "ru"
        Language.FRENCH -> "fr"
        Language.GERMAN -> "de"
    }
}

fun List<ArticleDBModel>.toEntities(): List<Article> {
    return map {
        Article(
            title = it.title,
            description = it.description,
            imageUrl = it.imageUrl,
            sourceName = it.sourceName,
            publishedAt = it.publishedAt,
            url = it.url
        )
    }.distinct()
}

private fun String.toTimestamp(): Long {

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return dateFormatter.parse(this)?.time ?: System.currentTimeMillis()
}