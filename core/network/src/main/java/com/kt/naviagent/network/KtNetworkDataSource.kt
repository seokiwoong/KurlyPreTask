package com.kt.naviagent.network

import com.kt.naviagent.network.model.NetworkProductInfo
import com.kt.naviagent.network.model.NetworkSectionInfo

interface KtNetworkDataSource {
    suspend fun getSectionInfo(page: Int): NetworkSectionInfo

    suspend fun getSectionProductInfo(sectionId: Int?): NetworkProductInfo
}