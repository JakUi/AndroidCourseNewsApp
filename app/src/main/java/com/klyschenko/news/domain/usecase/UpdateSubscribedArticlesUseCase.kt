package com.klyschenko.news.domain.usecase

import com.klyschenko.news.domain.repository.NewsRepository
import javax.inject.Inject


class UpdateSubscribedArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke() {
        newsRepository.updateArticlesForAllSubscriptions()
    }
}