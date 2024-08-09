package com.kt.naviagent.core.domain

import androidx.paging.PagingData
import com.kt.naviagent.core.data.model.Section
import com.kt.naviagent.core.data.repository.KtMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


data class GetSectionInfoParams(
    val size: Int = 5,
    val page: Int = 1
)

class GetSectionInfoUseCase @Inject constructor(
    private val ktMainRepository: KtMainRepository
) : FlowUseCase<GetSectionInfoParams, PagingData<Section>>() {
    override fun execute(parameters: GetSectionInfoParams): Flow<PagingData<Section>> {
        return ktMainRepository.getSectionInfo(parameters.page)
    }
}
