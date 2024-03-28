package com.kurly.pretask.core.domain

import com.kurly.pretask.core.data.model.SectionInfo
import com.kurly.pretask.core.data.repository.KurlyMainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


data class GetSectionInfoParams(
    val page: Int = 1
)

class GetSectionInfoUseCase @Inject constructor(
    private val kurlyMainRepository: KurlyMainRepository
) : FlowUseCase<GetSectionInfoParams, SectionInfo>() {
    override fun execute(parameters: GetSectionInfoParams): Flow<SectionInfo> {
        return kurlyMainRepository.getSectionInfo(parameters.page)
    }
}