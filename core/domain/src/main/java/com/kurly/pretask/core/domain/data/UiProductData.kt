package com.kurly.pretask.core.domain.data

import com.kurly.pretask.core.data.model.Product
import java.text.NumberFormat
import java.util.UUID
import javax.annotation.concurrent.Immutable


@Immutable
data class UiProduct(
    val uuid: String = UUID.randomUUID().toString(),
    val id: Long,
    val name: String,
    val image: String,
    val originalPrice: String,
    val discountPrice: String,
    val percent: String,
    val isWish: Boolean
)


fun Product.toUiProduct(formatter: NumberFormat, isWish: Boolean = false) = UiProduct(
    id = id,
    name = name,
    image = image,
    originalPrice = formatter.format(originalPrice),
    discountPrice = discountedPrice?.let { formatter.format(it) } ?: "",
    percent = discountedPrice?.let {
        "${((1 - (it.toDouble() / originalPrice)) * 100).toInt()}%"
    } ?: "",
    isWish = isWish
)
