package com.arbelkilani.bingetv

import android.app.Application
import com.arbelkilani.bingetv.presentation.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BingeTvApp : Application() {

    private val TAG = BingeTvApp::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

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