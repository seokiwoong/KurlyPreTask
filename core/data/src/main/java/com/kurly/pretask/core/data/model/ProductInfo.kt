package com.kurly.pretask.core.data.model

import com.kurly.pretask.network.model.NetworkProduct
import com.kurly.pretask.network.model.NetworkProductInfo


data class ProductInfo(
    val data: List<Product>,
)

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val originalPrice: Long,
    val discountPrice: Long?,
    val isSoldOut: Boolean
)


fun NetworkProduct.toProduction() =
    Product(
        id = id,
        name = name,
        image = image,
        originalPrice = originalPrice,
        discountPrice = discountPrice,
        isSoldOut = isSoldOut
    )


fun NetworkProductInfo.toProductInfo() =
    ProductInfo(data =
        data.map {
            it.toProduction()
        }
    )