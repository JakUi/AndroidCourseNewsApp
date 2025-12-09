package com.klyschenko.news.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionDBModel(@PrimaryKey val topic: String)
