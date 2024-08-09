package com.kt.naviagent.core.data.di

import com.kt.naviagent.core.data.repository.KtMainRepository
import com.kt.naviagent.core.data.repository.NetworkKtMainMainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataBindModule {

    @Binds
    fun binds(
        networkktMainRepository: NetworkKtMainMainRepository
    ): KtMainRepository

}
