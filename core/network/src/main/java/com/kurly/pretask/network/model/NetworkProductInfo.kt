package com.kurly.pretask.network.model

import kotlinx.serialization.Serializable


@Serializable
data class NetworkProductInfo(
    val data: List<NetworkProduct>,
)

@Serializable
data class NetworkProduct(
    val id: Long,
    val name: String,
    val image: String,
    val originalPrice: Long,
    val discountPrice: Long,
    val isSoldOut: Boolean
)