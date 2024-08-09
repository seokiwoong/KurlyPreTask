package com.kt.naviagent.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponseError(
    val code: Int,
    val msg: String
)