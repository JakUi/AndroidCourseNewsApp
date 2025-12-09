package com.klyschenko.news.domain.usecase

import com.klyschenko.news.domain.repository.NewsRepository
import javax.inject.Inject


class AddSubscriptionsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(topic: String) {
        newsRepository.addSubscription(topic)
        newsRepository.updateArticlesForTopic(topic)
    }
}