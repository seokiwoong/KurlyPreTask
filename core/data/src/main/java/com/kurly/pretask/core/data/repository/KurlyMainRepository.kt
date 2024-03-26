package com.kurly.pretask.core.data.repository

import com.kurly.pretask.core.data.model.ProductInfo
import com.kurly.pretask.core.data.model.SectionInfo
import kotlinx.coroutines.flow.Flow

interface KurlyMainRepository {
    suspend fun getSectionInfo(page: Int): Flow<SectionInfo>

    suspend fun getSectionProductInfo(sectionId: Int): Flow<ProductInfo>
}