package com.kurly.pretask.core.data.repository

import com.kurly.pretask.core.data.model.ProductInfo
import com.kurly.pretask.core.data.model.SectionInfo
import kotlinx.coroutines.flow.Flow

interface KurlyMainRepository {
    fun getSectionInfo(page: Int): Flow<SectionInfo>

    fun getSectionProductInfo(sectionId: Long): Flow<ProductInfo>
}