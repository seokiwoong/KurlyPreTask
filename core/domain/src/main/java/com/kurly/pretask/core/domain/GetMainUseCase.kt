package com.kurly.pretask.core.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.kurly.pretask.core.data.model.Section
import com.kurly.pretask.core.domain.common.Result
import com.kurly.pretask.core.domain.data.SectionType
import com.kurly.pretask.core.domain.data.UiData
import com.kurly.pretask.core.domain.data.toUiData
import com.kurly.pretask.core.domain.data.toUiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import java.text.DecimalFormat
import javax.inject.Inject

private fun String.toSectionType(): SectionType =
    when (this) {
        "header" -> SectionType.Header
        "grid" -> SectionType.Grid
        "vertical" -> SectionType.Vertical
        "horizontal" -> SectionType.Horizontal
        else -> SectionType.Empty
    }

class GetMainDataUseCase @Inject constructor(
    private val getSectionInfoUseCase: GetSectionInfoUseCase,
) {

    private val format = DecimalFormat("#,###원")

    fun execute(): Flow<PagingData<UiData>> {
        return getSectionInfoUseCase(GetSectionInfoParams())
            .filterIsInstance<Result.Success<PagingData<Section>>>()
            .map { result ->
                result.data.map { section ->
                    section.toUiData(
                        section.type.toSectionType(),
                    ).also {
                        it.productList = section.productList?.map {
                            it.toUiProduct(format)
                        } ?: emptyList()
                    }
                }
            }
    }
}
