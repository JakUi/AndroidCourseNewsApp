package com.klyschenko.news.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM subscriptions")
    fun getAllSubscriptions(): Flow<List<SubscriptionDBModel>>

    @Insert(onConflict = IGNORE)
    suspend fun addSubscription(subscriptionDBModel: SubscriptionDBModel)

    @Transaction
    @Delete
    suspend fun deleteSubscription(subscriptionDBModel: SubscriptionDBModel)

    @Query("SELECT * FROM articles WHERE topic IN (:topics) ORDER BY publishedAt DESC")
    fun getAllArticlesByTopics(topics: List<String>): Flow<List<ArticleDBModel>>

    @Insert(onConflict = IGNORE)
    suspend fun addArticles(articles: List<ArticleDBModel>): List<Long>

    @Query("DELETE FROM articles WHERE topic IN (:topics)")
    suspend fun deleteArticlesByTopics(topics: List<String>)
}