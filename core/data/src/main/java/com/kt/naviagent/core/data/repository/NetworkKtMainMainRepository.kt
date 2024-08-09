package com.kt.naviagent.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kt.naviagent.core.data.model.ProductInfo
import com.kt.naviagent.core.data.model.Section
import com.kt.naviagent.core.data.model.toProductInfo
import com.kt.naviagent.network.KtNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkKtMainMainRepository @Inject constructor(
    private val ktNetworkDataSource: KtNetworkDataSource,
) : KtMainRepository {

    override fun getSectionInfo(page: Int): Flow<PagingData<Section>> =
        Pager(
            config = PagingConfig(pageSize = 5, prefetchDistance = 2),
            pagingSourceFactory = {
                KtSectionInfoPagingSource(ktNetworkDataSource)
            }
        ).flow

    override fun getSectionProductInfo(sectionId: Int?): Flow<ProductInfo> =
        flow {
            emit(ktNetworkDataSource.getSectionProductInfo(sectionId).toProductInfo())
        }
}
