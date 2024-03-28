package com.kurly.pretask.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NetworkSectionInfo (
    val data : List<NetworkSection>,
    val paging : NetworkPageInfo
)

@Serializable
data class NetworkSection(
    val title : String,
    val id : Long,
    val type : String,
    val url : String
)

@Serializable
data class NetworkPageInfo(
    @SerialName("next_page")
    val nextPage : String
)