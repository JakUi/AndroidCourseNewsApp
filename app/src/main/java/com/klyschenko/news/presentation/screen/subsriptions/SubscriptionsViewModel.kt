@file:OptIn(ExperimentalCoroutinesApi::class)

package com.klyschenko.news.presentation.screen.subsriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klyschenko.news.domain.entity.Article
import com.klyschenko.news.domain.usecase.AddSubscriptionsUseCase
import com.klyschenko.news.domain.usecase.ClearAllArticlesUseCase
import com.klyschenko.news.domain.usecase.GetAllSubscriptionsUseCase
import com.klyschenko.news.domain.usecase.GetArticlesByTopicsUseCase
import com.klyschenko.news.domain.usecase.RemoveSubscriptionsUseCase
import com.klyschenko.news.domain.usecase.UpdateSubscribedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionsViewModel @Inject constructor(
    private val addSubscriptionsUseCase: AddSubscriptionsUseCase,
    private val clearAllArticlesUseCase: ClearAllArticlesUseCase,
    private val getAllSubscriptionsUseCase: GetAllSubscriptionsUseCase,
    private val getArticlesByTopicsUseCase: GetArticlesByTopicsUseCase,
    private val removeSubscriptionsUseCase: RemoveSubscriptionsUseCase,
    private val updateSubscribedArticlesUseCase: UpdateSubscribedArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SubscriptionsState())
    val state = _state.asStateFlow()

    init {
        observeSubscriptions()
        observeSelectedTopics()
    }

    fun processCommand(command: SubscriptionsCommands) {
        when (command) {
            SubscriptionsCommands.ClearArticles -> {
                viewModelScope.launch {
                    val topics = state.value.selectedTopics
                    clearAllArticlesUseCase(topics)
                }
            }

            SubscriptionsCommands.ClickSubscribe -> {
                viewModelScope.launch {
                    // очищаем поле ввода
                    _state.update { previousState ->
                        val topic = state.value.query.trim()
                        addSubscriptionsUseCase(topic)
                        previousState.copy(query = "")
                    }
                }
            }

            is SubscriptionsCommands.InputTopic -> {
                _state.update { previousState ->
                    previousState.copy(query = previousState.query)
                }
            }

            SubscriptionsCommands.RefreshData -> {
                viewModelScope.launch {
                    updateSubscribedArticlesUseCase()
                }
            }

            is SubscriptionsCommands.RemoveSubscription -> {
                viewModelScope.launch {
                    removeSubscriptionsUseCase(command.topic)
                }
            }

            is SubscriptionsCommands.ToggleTopicSelection -> {
                _state.update { previousState ->
                    val subscriptions = previousState.subscriptions.toMutableMap()
                    val isSelected = subscriptions[command.topic] ?: false
                    subscriptions[command.topic] = !isSelected
                    previousState.copy(subscriptions = subscriptions)
                }
            }
        }
    }

    private fun observeSelectedTopics() {
        state.map { it.selectedTopics }
            .distinctUntilChanged()
            .flatMapLatest {
                getArticlesByTopicsUseCase(it)
            }.onEach {
                _state.update {previousState ->
                    previousState.copy(articles = it)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSubscriptions() {
        getAllSubscriptionsUseCase()
            .onEach { subscriptions ->
                _state.update {previousState ->
                    val updateTopics = subscriptions.associateWith { topic ->
                        previousState.subscriptions[topic] ?: true // если эта тема была показана -
                                                                   // возвращаем значение ключа
                                                                    // который был иначе - показываем тему
                    }
                    previousState.copy(subscriptions = updateTopics)
                }
            }.launchIn(viewModelScope)
    }
}

sealed interface SubscriptionsCommands {

    data class InputTopic(val query: String) : SubscriptionsCommands

    data object ClickSubscribe : SubscriptionsCommands

    data object RefreshData : SubscriptionsCommands

    data class ToggleTopicSelection(val topic: String) : SubscriptionsCommands

    data object ClearArticles : SubscriptionsCommands

    data class RemoveSubscription(val topic: String) : SubscriptionsCommands
}

data class SubscriptionsState(
    val query: String = "",
    val subscriptions: Map<String, Boolean> = mapOf(),
    val articles: List<Article> = listOf()
) {

    val subscribeButtonEnabled: Boolean
        get() = query.isNotBlank()

    val selectedTopics: List<String>
        get() = subscriptions.filter { it.value }
            .map { it.key }  // оставляем только ключи со значением true
}