package com.kurly.android.mockserver.di

import com.kurly.android.mockserver.core.FileProvider
import com.kurly.android.mockserver.core.TestAssetFileProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MockServerModule {
    @Provides
    @Singleton
    fun provideTestFileProvider(): FileProvider = TestAssetFileProvider()

}