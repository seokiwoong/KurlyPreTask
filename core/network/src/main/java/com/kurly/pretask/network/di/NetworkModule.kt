package com.kurly.pretask.network.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.kurly.android.mockserver.MockInterceptor
import com.kurly.pretask.core.network.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun httpLoggingInterceptor() =
        HttpLoggingInterceptor()
            .apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            }


    @Named("mock")
    @Provides
    @Singleton
    fun okHttpCallFactoryForMock(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                MockInterceptor(context)
            )
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Named("image")
    @Provides
    @Singleton
    fun okHttpCallFactoryForImage(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                MockInterceptor(context)
            )
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun providesKotlinJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun imageLoader(
        @Named("image") okHttpCallFactory: dagger.Lazy<Call.Factory>,
        @ApplicationContext application: Context,
    ): ImageLoader =
        ImageLoader.Builder(application)
            .callFactory { okHttpCallFactory.get() }
            .respectCacheHeaders(false)
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            .build()
}