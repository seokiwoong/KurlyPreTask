package com.kt.naviagent.network

import com.kt.naviagent.network.core.FileProvider
import com.kt.naviagent.network.core.TestAssetFileProvider
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