package com.kurly.pretask.network

import com.kurly.pretask.network.core.FileProvider
import com.kurly.pretask.network.core.TestAssetFileProvider
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
    fun provideTestFileProvider(): FileProvider =
        TestAssetFileProvider()

}