package com.kurly.pretask.core.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kurly.pretask.core.data.datastore.UserWishProductData
import com.kurly.pretask.core.data.model.Product
import com.kurly.pretask.core.datastore.UserWishProductPreferences
import com.kurly.pretask.core.datastore.WishProductInfo
import com.kurly.pretask.core.datastore.copy
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class KurlyPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserWishProductPreferences>,
) {
    val userWishProductPreferenceData = userPreferences.data
        .map {
            UserWishProductData(
                wishProductInfoList = it.wishProductInfosList
            )
        }

    suspend fun setWishProduct(product: Product, wish: Boolean) {
        try {
            userPreferences.updateData { userWishProductPreferences ->
                val index = userWishProductPreferences.wishProductInfosList
                    .indexOfFirst { cachedData -> cachedData.id == product.id }
                    .takeIf { it >= 0 }
                if (wish && index == null) {
                    userWishProductPreferences.copy {
                        this.wishProductInfos.add(
                            WishProductInfo.getDefaultInstance().copy {
                                id = product.id
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
