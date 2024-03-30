package com.kurly.pretask.network.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.kurly.pretask.core.network.BuildConfig
import com.kurly.pretask.network.KurlyNetworkDataSource
import com.kurly.pretask.network.RetrofitKurly
import com.kurly.pretask.network.mock.MockInterceptor
import dagger.Binds
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
interface NetworkModule {
    @Binds
    fun bindsKurlyNetwork(
        retrofitKurly: RetrofitKurly
    ): KurlyNetworkDataSource


    companion object {
        @Provides
        @Singleton
        fun providesHttpLoggingInterceptor() =
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                }


        @Named("mock")
        @Provides
        @Singleton
        fun providesOkHttpCallFactoryForMock(
            @ApplicationContext context: Context,
            httpLoggingInterceptor: HttpLoggingInterceptor
        ): Call.Factory =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(MockInterceptor(context))
                .build()


        @Named("image")
        @Provides
        @Singleton
        fun providesOkHttpCallFactoryForImage(
            httpLoggingInterceptor: HttpLoggingInterceptor
        ): Call.Factory =
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()


        @Provides
        @Singleton
        fun providesKotlinJson(): Json = Json {
            ignoreUnknownKeys = true
        }

        @Provides
        @Singleton
        fun providesImageLoader(
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
}