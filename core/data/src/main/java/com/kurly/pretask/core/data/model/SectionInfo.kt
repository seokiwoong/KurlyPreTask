package com.kurly.pretask.core.data.model

import com.kurly.pretask.network.model.NetworkPageInfo
import com.kurly.pretask.network.model.NetworkSection
import com.kurly.pretask.network.model.NetworkSectionInfo

data class SectionInfo(
    val data: List<Section>,
    val paging: PageInfo?
)

data class Section(
    val title: String,
    val id: Long,
    val type: String,
    val productList: List<Product>? = null
)

data class PageInfo(
    val nextPage: Int
)

fun NetworkPageInfo.toPageInfo() =
    PageInfo(nextPage = nextPage)


fun NetworkSection.toSection() = Section(
    title = title,
    id = id,
    type = type
)

fun NetworkSectionInfo.toSectionInfo() = SectionInfo(
    data = data.map { it.toSection() },
    paging = paging?.toPageInfo()
)