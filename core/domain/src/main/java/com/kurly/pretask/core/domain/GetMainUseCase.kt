package com.kurly.pretask.core.domain

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kurly.pretask.core.data.model.SectionInfo
import com.kurly.pretask.core.domain.common.Result
import com.kurly.pretask.core.domain.data.SectionType
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.toUiData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


data class PageKeyData(
    val page: Int?,
)

private fun String.toSectionType(): SectionType =
    when (this) {
        "grid" -> SectionType.grid
        "vertical" -> SectionType.vertical
        "horizontal" -> SectionType.horizontal
        else -> SectionType.empty
    }

class GetMainDataUseCase @Inject constructor(
    private val getSectionInfoUseCase: GetSectionInfoUseCase,
    private val getSectionProductInfoUseCase: GetSectionProductInfoUseCase,
) {
    fun execute(parameters: GetSectionInfoParams): Flow<PagingData<UiData>> =
        Pager(config = PagingConfig(parameters.size), pagingSourceFactory = {
            SectionInfoPagingSource(
                getSectionInfoList = { params: GetSectionInfoParams ->
                    getSectionInfoUseCase(params)
                        .filterIsInstance<Result.Success<SectionInfo>>()
                        .map {
                            Pair(
                                it.data.paging == null,
                                it.data.data.map {
                                    it.toUiData(
                                        it.type.toSectionType(),
                                        it.url.substring(it.url.lastIndexOf("_") + 1).toInt()
                                    )
                                })
                        }
                }, parameters
            )
        }).flow

}


internal class SectionInfoPagingSource(
    private val getSectionInfoList: (GetSectionInfoParams) -> Flow<Pair<Boolean, List<UiData>>>,
    private val parameters: GetSectionInfoParams,
) : PagingSource<PageKeyData, UiData>() {

    override suspend fun load(params: LoadParams<PageKeyData>): LoadResult<PageKeyData, UiData> {
        return try {
            if (parameters.page == 0) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            val initialPage = 1
            val requestKey = params.key ?: PageKeyData(
                initialPage
            )
            val requestPage = requestKey.page ?: initialPage
            val requestParameter = parameters.copy(page = requestPage)
            val (endOfPage, data) = getSectionInfoList(requestParameter).single()

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
                data = data.toMutableList().apply {
                    if (isNullOrEmpty()) return@apply
                },
                prevKey = null,
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
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<PageKeyData, UiData>): PageKeyData? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
