package com.kurly.pretask.network

import com.kurly.pretask.network.model.NetworkProductInfo
import com.kurly.pretask.network.model.NetworkSectionInfo

interface KurlyNetworkDataSource {
    suspend fun getSectionInfo(page: Int): NetworkSectionInfo

    suspend fun getSectionProductInfo(sectionId: Int?): NetworkProductInfo
}