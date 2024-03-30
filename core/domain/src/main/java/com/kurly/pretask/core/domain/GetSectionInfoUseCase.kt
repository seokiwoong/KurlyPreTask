package com.kurly.pretask.core.domain

import androidx.paging.PagingData
import com.kurly.pretask.core.data.model.Section
import com.kurly.pretask.core.data.repository.KurlyMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


data class GetSectionInfoParams(
    val size: Int = 5,
    val page: Int = 1
)

class GetSectionInfoUseCase @Inject constructor(
    private val kurlyMainRepository: KurlyMainRepository
) : FlowUseCase<GetSectionInfoParams, PagingData<Section>>() {
    override fun execute(parameters: GetSectionInfoParams): Flow<PagingData<Section>> {
        return kurlyMainRepository.getSectionInfo(parameters.page)
    }
}
