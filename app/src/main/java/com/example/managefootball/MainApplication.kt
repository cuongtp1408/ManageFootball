package com.example.managefootball

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.managefootball.repository.FootballRepository
import com.example.managefootball.util.AddMatchWorker

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication: Application(), Configuration.Provider {
    @Inject
    lateinit var addMatchWorkerFactory: AddMatchWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(addMatchWorkerFactory).build()

}

class AddMatchWorkerFactory @Inject constructor(private val footballRepository: FootballRepository) : WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = AddMatchWorker(appContext,workerParameters,footballRepository)

}