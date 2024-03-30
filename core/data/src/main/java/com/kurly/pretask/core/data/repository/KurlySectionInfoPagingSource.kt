package com.kurly.pretask.core.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kurly.pretask.core.data.model.Section
import com.kurly.pretask.core.data.model.toProduction
import com.kurly.pretask.core.data.model.toSectionInfo
import com.kurly.pretask.network.KurlyNetworkDataSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException

class KurlySectionInfoPagingSource(
    private val kurlyNetworkDataSource: KurlyNetworkDataSource,
) : PagingSource<PageKeyData, Section>() {
    override suspend fun load(params: LoadParams<PageKeyData>): LoadResult<PageKeyData, Section> {
        return try {
            val initialPaging = 1
            val requestKey = params.key ?: PageKeyData(
                initialPaging
            )

            val requestPage = requestKey.page ?: initialPaging
            val sectionInfo = kurlyNetworkDataSource.getSectionInfo(requestPage).toSectionInfo()
            val endOfPage = sectionInfo.paging == null

            val updatedSectionInfo = coroutineScope {
                val deferred = mutableListOf<Deferred<Section>>()
                sectionInfo.data.forEach { section ->
                    deferred.add(
                        async(Dispatchers.IO) {
                            val product =
                                kurlyNetworkDataSource.getSectionProductInfo(section.id.toInt())
                            section.copy(productList = product.data.map { it.toProduction() })
                        })
                }
                deferred.awaitAll()
            }
            val nextKey = if (endOfPage) {
                null
            } else {
                requestPage.plus(1)
            }

            Log.i(
                "PagingSource",
                "endOfPage = $endOfPage"
            )
            LoadResult.Page(
                data = updatedSectionInfo,
                prevKey = if (requestPage == 1) null
                else PageKeyData(requestPage - 1),
                nextKey = nextKey?.let {
                    PageKeyData(
                        nextKey
                    )
                }
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<PageKeyData, Section>): PageKeyData? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}


data class PageKeyData(
    val page: Int?,
)
