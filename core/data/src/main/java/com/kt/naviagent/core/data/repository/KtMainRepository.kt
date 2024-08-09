package com.kt.naviagent.core.data.repository

import androidx.paging.PagingData
import com.kt.naviagent.core.data.model.ProductInfo
import com.kt.naviagent.core.data.model.Section
import kotlinx.coroutines.flow.Flow

interface KtMainRepository {
    fun getSectionInfo(page: Int): Flow<PagingData<Section>>

    fun getSectionProductInfo(sectionId: Int?): Flow<ProductInfo>
}