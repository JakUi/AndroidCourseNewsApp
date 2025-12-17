package com.klyschenko.news.data.mapper

import com.klyschenko.news.domain.entity.RefreshConfig
import com.klyschenko.news.domain.entity.Settings

fun Settings.toRefreshConfig(): RefreshConfig {

    return RefreshConfig(language, interval, wifiOnly)
}