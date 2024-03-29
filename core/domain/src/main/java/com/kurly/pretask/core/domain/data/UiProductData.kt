package com.kurly.pretask.core.domain.data

import com.kurly.pretask.core.data.model.Product
import java.text.NumberFormat


data class UiProduct(
    val id: Long,
    val name: String,
    val image: String,
    val originalPrice: String,
    val discountPrice: String?,
    val percent: String?,
    val isWish: Boolean
)


fun Product.toUiProduct(formatter: NumberFormat, isWish: Boolean) = UiProduct(
    id = id,
    name = name,
    image = image,
    originalPrice = formatter.format(originalPrice),
    discountPrice = discountPrice?.let { formatter.format(it) },
    percent = discountPrice?.let {
        String.format("%d%", it.toDouble() / originalPrice * 100)
    },
    isWish = isWish
)
