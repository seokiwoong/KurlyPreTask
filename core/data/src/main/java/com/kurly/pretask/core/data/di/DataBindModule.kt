package com.kurly.pretask.core.data.di

import com.kurly.pretask.core.data.repository.KurlyMainRepository
import com.kurly.pretask.core.data.repository.NetworkKurlyMainMainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataBindModule {

    @Binds
    fun binds(
        networkKurlyMainRepository: NetworkKurlyMainMainRepository
    ): KurlyMainRepository

}
