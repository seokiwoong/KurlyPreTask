package com.kurly.pretask.core.data.repository

import androidx.paging.PagingData
import com.kurly.pretask.core.data.model.ProductInfo
import com.kurly.pretask.core.data.model.Section
import kotlinx.coroutines.flow.Flow

interface KurlyMainRepository {
    fun getSectionInfo(page: Int): Flow<PagingData<Section>>

    fun getSectionProductInfo(sectionId: Int?): Flow<ProductInfo>
}