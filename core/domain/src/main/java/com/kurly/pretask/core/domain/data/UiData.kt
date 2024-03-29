package com.kurly.pretask.core.domain.data

import com.kurly.pretask.core.data.model.Section
import javax.annotation.concurrent.Immutable


sealed interface SectionType {
    data object header : SectionType
    data object vertical : SectionType
    data object  horizontal : SectionType
    data object  grid : SectionType
    data object  empty : SectionType
}

@Immutable
data class UiData (
    val title: String,
    val id: Long,
    val type: SectionType,
    val productId : Int?,
    val productList : List<UiProduct>? = null
)


fun Section.toUiData(type: SectionType, productId: Int?) = UiData(
    title = title,
    id = id,
    type = type,
    productId = productId,
    productList = null
)
