package com.klyschenko.news.domain.usecase

import com.klyschenko.news.domain.entity.Language
import com.klyschenko.news.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(

    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(language: Language) {
        settingsRepository.updateLanguage(language)
    }
}