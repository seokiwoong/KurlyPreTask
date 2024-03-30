package com.kurly.pretask.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponseError(
    val code: Int,
    val msg: String
)