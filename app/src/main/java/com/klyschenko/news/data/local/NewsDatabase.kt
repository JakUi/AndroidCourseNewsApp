package com.klyschenko.news.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleDBModel::class, SubscriptionDBModel::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
}
