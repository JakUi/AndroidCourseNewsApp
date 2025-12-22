package com.klyschenko.news.presentation.screen.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klyschenko.news.data.mapper.toInterval
import com.klyschenko.news.domain.entity.Interval
import com.klyschenko.news.domain.entity.Language
import com.klyschenko.news.domain.entity.Settings
import com.klyschenko.news.domain.usecase.GetSettingsUseCase
import com.klyschenko.news.domain.usecase.NotificationUpdatedUseCase
import com.klyschenko.news.domain.usecase.UpdateIntervalUseCase
import com.klyschenko.news.domain.usecase.UpdateLanguageUseCase
import com.klyschenko.news.domain.usecase.UpdateWifiOnlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val notificationUpdatedUseCase: NotificationUpdatedUseCase,
    private val updateIntervalUseCase: UpdateIntervalUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val updateWifiOnlyUseCase: UpdateWifiOnlyUseCase
) : ViewModel() {


    val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        updateSettings()
    }

    fun processCommand(command: SettingsCommands) {
        when (command) {

            is SettingsCommands.NotificationUpdate -> {
                viewModelScope.launch {
                    Log.d("Debug", "Command is ${command.enabled}")
                    notificationUpdatedUseCase(command.enabled)
                }
            }

            is SettingsCommands.UpdateInterval -> {
                viewModelScope.launch {
                    Log.d("Debug", "Interval chosen: ${command.minutes.toInterval()}")
                    updateIntervalUseCase(interval = command.minutes.toInterval())
                }
            }

            is SettingsCommands.UpdateLanguage -> {
                viewModelScope.launch {
                    Log.d("Debug", "Language chosen: ${command.language}")
                    updateLanguageUseCase(language = command.language)
                }
            }

            is SettingsCommands.UpdateWifiOnly -> {
                viewModelScope.launch {
                    updateWifiOnlyUseCase(command.wifiOnly)
                }
            }
        }
    }

    private fun updateSettings() {
        getSettingsUseCase()
            .onEach { setting ->
                _state.update { previousState ->
                    val newState = previousState.copy(
                        language = setting.language,
                        interval = setting.interval,
                        notificationsEnabled = setting.notificationsEnabled,
                        wifiOnly = setting.wifiOnly
                    )
                    Log.d("Debug", "Update settings method, previous state is: $newState")
                    newState
                }
            }.launchIn(viewModelScope)
    }

}

sealed interface SettingsCommands {

    data class NotificationUpdate(val enabled: Boolean) : SettingsCommands

    data class UpdateInterval(val minutes: Int) : SettingsCommands

    data class UpdateLanguage(val language: Language) : SettingsCommands

    data class UpdateWifiOnly(val wifiOnly: Boolean) : SettingsCommands
}

data class SettingsState( // Нужен рефакторинг ???????
    val language: Language = Settings.DEFAULT_LANGUAGE,
    val interval: Interval = Settings.DEFAULT_INTERVAL,
    val notificationsEnabled: Boolean = Settings.DEFAULT_NOTIFICATION_ENABLED,
    val wifiOnly: Boolean = Settings.DEFAULT_WIFI_ONLY
)
