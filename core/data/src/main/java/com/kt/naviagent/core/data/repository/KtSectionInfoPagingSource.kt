package com.kt.naviagent.core.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kt.naviagent.core.data.model.Section
import com.kt.naviagent.core.data.model.toProduction
import com.kt.naviagent.core.data.model.toSectionInfo
import com.kt.naviagent.network.KtNetworkDataSource
import com.kt.naviagent.network.model.NetworkProductInfo
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.IOException

class KtSectionInfoPagingSource(
    private val ktNetworkDataSource: KtNetworkDataSource,
) : PagingSource<PageKeyData, Section>() {
    override suspend fun load(params: LoadParams<PageKeyData>): LoadResult<PageKeyData, Section> {
        return try {
            val initialPaging = 1
            val requestKey = params.key ?: PageKeyData(
                initialPaging
            )

            val requestPage = requestKey.page ?: initialPaging
            val sectionInfo = ktNetworkDataSource.getSectionInfo(requestPage).toSectionInfo()
            val endOfPage = sectionInfo.paging == null

            val updatedSectionInfo = coroutineScope {
                val deferred = mutableListOf<Deferred<Section>>()
                sectionInfo.data.forEach { section ->
                    deferred.add(
                        async(Dispatchers.IO) {
                            val product =
                                ktNetworkDataSource.getSectionProductInfo(section.id.toInt())
                            section.copy(
                                productList =
                                if (isOverflowGridItem(section, product)) {
                                    product.data.subList(0, 5).map { it.toProduction() }
                                } else {
                                    product.data.map { it.toProduction() }
                                }
                            )
                        })
                }
                deferred.awaitAll()
            }

            val dataResult = mutableListOf<Section>()
            updatedSectionInfo.forEach { section ->
                dataResult.add(section.copy(type = "header"))
                if (section.type == "vertical") {
                    section.productList?.map {
                        dataResult.add(section.copy(productList = listOf(it)))
                    }
                }
                dataResult.add(section)
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
                data = dataResult,
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

    private fun isOverflowGridItem(
        section: Section,
        product: NetworkProductInfo
    ) = section.type == "grid" && product.data.size > 6

    override fun getRefreshKey(state: PagingState<PageKeyData, Section>): PageKeyData? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}


data class PageKeyData(
    val page: Int?,
)
