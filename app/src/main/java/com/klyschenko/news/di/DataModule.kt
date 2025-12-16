package com.klyschenko.news.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.klyschenko.news.data.local.NewsDao
import com.klyschenko.news.data.local.NewsDatabase
import com.klyschenko.news.data.remote.NewsApiService
import com.klyschenko.news.data.repository.NewsRepositoryImpl
import com.klyschenko.news.data.repository.SettingsRepositoryImpl
import com.klyschenko.news.domain.repository.NewsRepository
import com.klyschenko.news.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindNewsRepository(
        impl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository


    companion object {

        @Provides
        @Singleton
        fun provideWorkManager(
            @ApplicationContext context: Context
        ): WorkManager = WorkManager.getInstance(context)

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

        @Provides
        @Singleton
        fun provideConverterFactory(
            json: Json
        ): Converter.Factory {
            return json.asConverterFactory(
                    contentType = "application/json".toMediaType()
                )
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            converterFactory: Converter.Factory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(converterFactory)
                .build()
        }

        // метод ниже это реализация интерфейса NewsApiService через библиотеку retrofit
        @Provides
        @Singleton
        fun provideApiService(
            retrofit: Retrofit
        ): NewsApiService {
            return retrofit.create<NewsApiService>() // в угловых скобках можно указать тип интерфейса который мы ожидаем
        }

        @Singleton
        @Provides
        fun provideNewsDatabase(
            @ApplicationContext context: Context
        ): NewsDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = NewsDatabase::class.java,
                name = "news.db"
            ).fallbackToDestructiveMigration(dropAllTables = true).build()
        }

        @Singleton
        @Provides
        fun provideNewsDao(
            database: NewsDatabase
        ): NewsDao = database.newsDao()
    }
}