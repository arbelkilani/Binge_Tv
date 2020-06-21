package com.arbelkilani.bingetv.presentation.di

import android.util.Log
import com.arbelkilani.bingetv.BuildConfig
import com.arbelkilani.bingetv.data.source.remote.EndpointInterceptor
import com.arbelkilani.bingetv.data.source.remote.ApiTmdbService
import com.arbelkilani.bingetv.data.source.remote.ApiTvMazeService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetworkModule = module {

    single { createOkHttpClient() }

    single { createRetrofit(get()) }

    single { createTmdbService(get()) }

    single { createTvMazeService(get()) }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    val endpointInterceptor =
        EndpointInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(endpointInterceptor)
        .build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createTmdbService(retrofit: Retrofit): ApiTmdbService {
    Log.i("TAG++", "retrofit tmdb = $retrofit, api ${retrofit.baseUrl()}")
    return retrofit.create(ApiTmdbService::class.java)
}

fun createTvMazeRetrofitBuilder(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.TVMAZE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun createTvMazeService(okHttpClient: OkHttpClient): ApiTvMazeService {
    val builder = Retrofit.Builder()
        .baseUrl(BuildConfig.TVMAZE_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    Log.i("TAG++", "retrofit tvmaze = $builder, api ${builder.baseUrl()}")
    return builder.create(ApiTvMazeService::class.java)
}

