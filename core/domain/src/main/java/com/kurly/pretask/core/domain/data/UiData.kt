package com.kurly.pretask.core.domain.data

import com.kurly.pretask.core.data.model.Section
import java.util.UUID
import javax.annotation.concurrent.Immutable


sealed interface SectionType {
    @Immutable
    data object vertical : SectionType

    @Immutable
    data object horizontal : SectionType

    @Immutable
    data object grid : SectionType

    @Immutable
    data object empty : SectionType
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
