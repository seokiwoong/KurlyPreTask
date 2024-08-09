package com.kt.naviagent.core.domain

import com.kt.naviagent.core.data.repository.KtMainRepository
import com.kt.naviagent.core.data.repository.KtPreferencesDataSource
import com.kt.naviagent.core.domain.data.UiProduct
import com.kt.naviagent.core.domain.data.toUiProduct
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
    private val ktMainRepository: KtMainRepository,
    dataSource: KtPreferencesDataSource
) : FlowUseCase<GetSectionProductInfoParams, List<UiProduct>>() {

    private val format = DecimalFormat("#,###원")

    private val wishProductIdListFlow: Flow<List<Long>> = dataSource.userWishProductPreferenceData
        .map { it.wishProductInfoList }
        .distinctUntilChanged()


    override fun execute(parameters: GetSectionProductInfoParams): Flow<List<UiProduct>> =
        ktMainRepository.getSectionProductInfo(parameters.sectionId)
            .combine(wishProductIdListFlow) { productInfo, wishInfoList ->
                productInfo.data.map { data ->
                    data.toUiProduct(
                        formatter = format,
                        isWish = wishInfoList.any { id -> id == data.id }
                    )
                }
            }
}