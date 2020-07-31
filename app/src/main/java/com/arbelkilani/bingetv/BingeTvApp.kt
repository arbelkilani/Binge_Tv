package com.arbelkilani.bingetv

import android.app.Application
import com.arbelkilani.bingetv.presentation.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BingeTvApp : Application() {

    private val TAG = BingeTvApp::class.java.simpleName

    companion object {
        lateinit var instance: BingeTvApp
            private set
    }

    @FlowPreview
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidLogger()
            androidContext(this@BingeTvApp)
            modules(
                listOf(
                    SharedPreferencesModule,
                    DatabaseModule,
                    NetworkModule,
                    RepositoriesModule,
                    UseCasesModule,
                    ViewModelModule
                )
            )
        }
    }
}