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