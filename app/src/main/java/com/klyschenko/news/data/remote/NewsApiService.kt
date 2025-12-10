package com.klyschenko.news.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything?apiKey=908fa26c54694411a0cc7b96431a6d98")
    suspend fun loadArticles(
        @Query("q") topic: String // будет подставлено в url в качестве параметра
    ): NewsResponseDto // Retrofit сконвертирует ответ в экземпляр NewsResponseDto
}