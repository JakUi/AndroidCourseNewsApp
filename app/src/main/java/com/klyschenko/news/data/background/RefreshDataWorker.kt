package com.klyschenko.news.data.background

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.klyschenko.news.domain.usecase.UpdateSubscribedArticlesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val updateSubscribedArticlesUseCase: UpdateSubscribedArticlesUseCase,
    private val notificationsHelper: NotificationsHelper
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        Log.d("RefreshDataWorker", "Start")
        updateSubscribedArticlesUseCase()
        Log.d("RefreshDataWorker", "Finish")
        notificationsHelper.showNewArticlesNotification(listOf())
        return Result.success() // для метода doWork всегда нужно возвращать результат работы,
        // updateSubscribedArticlesUseCase() всегда выполняется успешно, поэтому возвращаем success()
    }
}