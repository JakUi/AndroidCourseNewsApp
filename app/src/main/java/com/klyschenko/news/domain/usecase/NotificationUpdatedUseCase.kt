package com.klyschenko.news.domain.usecase

import com.klyschenko.news.domain.repository.SettingsRepository
import javax.inject.Inject

class NotificationUpdatedUseCase @Inject constructor(

    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(enabled: Boolean) {
        settingsRepository.updateNotificationsEnabled(enabled)
    }
}