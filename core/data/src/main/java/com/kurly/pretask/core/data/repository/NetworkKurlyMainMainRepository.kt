package com.kurly.pretask.core.data.repository

import com.kurly.pretask.core.data.model.ProductInfo
import com.kurly.pretask.core.data.model.SectionInfo
import com.kurly.pretask.core.data.model.toProductInfo
import com.kurly.pretask.core.data.model.toSectionInfo
import com.kurly.pretask.network.KurlyNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkKurlyMainMainRepository @Inject constructor(
    private val kurlyNetworkDataSource: KurlyNetworkDataSource
) : KurlyMainRepository {

    override fun getSectionInfo(page: Int): Flow<SectionInfo> =
        flow {
            emit(kurlyNetworkDataSource.getSectionInfo(page).toSectionInfo())
        }

    override fun getSectionProductInfo(sectionId: Int?): Flow<ProductInfo> =
        flow {
            emit(kurlyNetworkDataSource.getSectionProductInfo(sectionId).toProductInfo())
        }
}
