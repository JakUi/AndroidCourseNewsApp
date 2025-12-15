package com.klyschenko.news.data.background

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.klyschenko.news.domain.usecase.UpdateSubscribedArticlesUseCase

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val updateSubscribedArticlesUseCase: UpdateSubscribedArticlesUseCase
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        Log.d("RefreshDataWorker", "Start")
        updateSubscribedArticlesUseCase()
        Log.d("RefreshDataWorker", "Finish")
        return Result.success() // для метода doWork всегда нужно возвращать результат работы,
        // updateSubscribedArticlesUseCase() всегда выполняется успешно, поэтому возвращаем success()
    }
}