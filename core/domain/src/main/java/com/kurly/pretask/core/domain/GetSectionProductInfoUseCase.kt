package com.kurly.pretask.core.domain

import com.kurly.pretask.core.data.model.ProductInfo
import com.kurly.pretask.core.data.repository.KurlyMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


data class GetSectionProductInfoParams(
    val sectionId: Long
)

class GetSectionProductInfoUseCase @Inject constructor(
    private val kurlyMainRepository: KurlyMainRepository
) : FlowUseCase<GetSectionProductInfoParams, ProductInfo>() {
    override fun execute(parameters: GetSectionProductInfoParams): Flow<ProductInfo> {
        return kurlyMainRepository.getSectionProductInfo(parameters.sectionId)
    }
}