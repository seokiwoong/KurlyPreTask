package com.kurly.pretask.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.kurly.pretask.core.datastore.UserUserWishProductPreferenceSerializer
import com.kurly.pretask.core.datastore.UserWishProductPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesUserWishProductPreferencesDataStore(
        @ApplicationContext context: Context,
        userPreferencesSerializer: UserUserWishProductPreferenceSerializer
    ): DataStore<UserWishProductPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(
                CoroutineScope(SupervisorJob() + Dispatchers.Default)
                    .coroutineContext + Dispatchers.IO
            )
        ) {
            context.dataStoreFile("user_wish_product_preferences.pb")
        }
}