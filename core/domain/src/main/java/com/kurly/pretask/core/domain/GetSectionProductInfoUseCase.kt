package com.kurly.pretask.core.domain

import com.kurly.pretask.core.data.repository.KurlyMainRepository
import com.kurly.pretask.core.data.repository.KurlyPreferencesDataSource
import com.kurly.pretask.core.domain.data.UiProduct
import com.kurly.pretask.core.domain.data.toUiProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.text.DecimalFormat
import javax.inject.Inject


data class GetSectionProductInfoParams(
    val sectionId: Int?
)

class GetSectionProductInfoUseCase @Inject constructor(
    private val kurlyMainRepository: KurlyMainRepository,
    dataSource: KurlyPreferencesDataSource
) : FlowUseCase<GetSectionProductInfoParams, List<UiProduct>>() {

    private val format = DecimalFormat("#,###원")

    private val wishProductIdListFlow: Flow<List<Long>> = dataSource.userWishProductPreferenceData
        .map { it.wishProductInfoList }
        .distinctUntilChanged()


    override fun execute(parameters: GetSectionProductInfoParams): Flow<List<UiProduct>> =
        kurlyMainRepository.getSectionProductInfo(parameters.sectionId)
            .combine(wishProductIdListFlow) { productInfo, wishInfoList ->
                productInfo.data.map { data ->
                    data.toUiProduct(
                        formatter = format,
                        isWish = wishInfoList.any { id -> id == data.id }
                    )
                }
            }
}