package com.kurly.pretask.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kurly.pretask.core.data.model.ProductInfo
import com.kurly.pretask.core.data.model.Section
import com.kurly.pretask.core.data.model.toProductInfo
import com.kurly.pretask.network.KurlyNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NetworkKurlyMainMainRepository @Inject constructor(
    private val kurlyNetworkDataSource: KurlyNetworkDataSource,
) : KurlyMainRepository {

    override fun getSectionInfo(page: Int): Flow<PagingData<Section>> =
        Pager(
            config = PagingConfig(pageSize = 5, prefetchDistance = 2),
            pagingSourceFactory = {
                KurlySectionInfoPagingSource(kurlyNetworkDataSource)
            }
        ).flow

    override fun getSectionProductInfo(sectionId: Int?): Flow<ProductInfo> =
        flow {
            emit(kurlyNetworkDataSource.getSectionProductInfo(sectionId).toProductInfo())
        }
}
