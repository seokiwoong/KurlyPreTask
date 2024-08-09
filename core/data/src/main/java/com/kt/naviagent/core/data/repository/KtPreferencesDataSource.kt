package com.kt.naviagent.core.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kt.naviagent.core.data.datastore.UserWishProductData
import com.kt.naviagent.core.datastore.UserWishProductPreferences
import com.kt.naviagent.core.datastore.WishProductInfo
import com.kt.naviagent.core.datastore.copy
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class KtPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserWishProductPreferences>,
) {
    val userWishProductPreferenceData = userPreferences.data
        .map {
            UserWishProductData(
                wishProductInfoList = it.wishProductInfosList.map { wish -> wish.id }
            )
        }

    suspend fun setWishProduct(productId: Long, wish: Boolean) {
        try {
            userPreferences.updateData { userWishProductPreferences ->
                val index = userWishProductPreferences.wishProductInfosList
                    .indexOfFirst { cachedData -> cachedData.id == productId }
                    .takeIf { it >= 0 }
                if (wish && index == null) {
                    userWishProductPreferences.copy {
                        this.wishProductInfos.add(
                            WishProductInfo.getDefaultInstance().copy {
                                id = productId
                            }
                        )
                    }
                } else {
                    index ?: return@updateData userWishProductPreferences
                    userWishProductPreferences.toBuilder().removeWishProductInfos(index).build()
                }
            }
        } catch (ioException: IOException) {
            Log.e("PreferencesDataSource", "Error updating data store", ioException)
        }
    }
}
