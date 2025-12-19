package com.klyschenko.news.data.remote

import com.klyschenko.news.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything?apiKey=${BuildConfig.NEWS_API_KEY}")
    suspend fun loadArticles(
        @Query("q") topic: String, // будет подставлено в url в качестве параметра
        @Query("language") language: String
    ): NewsResponseDto // Retrofit сконвертирует ответ в экземпляр NewsResponseDto
}