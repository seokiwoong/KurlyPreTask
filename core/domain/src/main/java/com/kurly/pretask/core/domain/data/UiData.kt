package com.kurly.pretask.core.domain.data

import com.kurly.pretask.core.data.model.Section
import java.util.UUID
import javax.annotation.concurrent.Immutable


sealed interface SectionType {
    data object Header : SectionType

    data object Vertical : SectionType

    data object Horizontal : SectionType

    data object Grid : SectionType

    data object Empty : SectionType
}

@Immutable
data class UiData(
    val uuid: String = UUID.randomUUID().toString(),
    val title: String,
    val id: Long,
    val type: SectionType,
    var productList: List<UiProduct>
)


fun Section.toUiData(type: SectionType) = UiData(
    title = title,
    id = id,
    type = type,
    productList = emptyList()
)
