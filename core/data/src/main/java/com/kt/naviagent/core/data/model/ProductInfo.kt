package com.kt.naviagent.core.data.model

import com.kt.naviagent.network.model.NetworkProduct
import com.kt.naviagent.network.model.NetworkProductInfo


data class ProductInfo(
    val data: List<Product>,
)

data class Product(
    val id: Long,
    val name: String,
    val image: String,
    val originalPrice: Long,
    val discountedPrice: Long?,
    val isSoldOut: Boolean
)


fun NetworkProduct.toProduction() =
    Product(
        id = id,
        name = name,
        image = image,
        originalPrice = originalPrice,
        discountedPrice = discountedPrice,
        isSoldOut = isSoldOut
    )


fun NetworkProductInfo.toProductInfo() =
    ProductInfo(data =
    data.map {
        it.toProduction()
    }
    )