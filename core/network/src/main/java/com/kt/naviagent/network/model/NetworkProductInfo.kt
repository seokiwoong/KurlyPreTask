package com.kt.naviagent.network.model

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
    val discountedPrice: Long? = null,
    val isSoldOut: Boolean
)