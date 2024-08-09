package com.kt.naviagent.network

import com.kt.naviagent.network.model.NetworkProductInfo
import com.kt.naviagent.network.model.NetworkSectionInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitktNetworkApi {
    /**
     * sections api
     */
    @GET("sections")
    suspend fun sections(
        @Query("page") page: Int
    ): NetworkSectionInfo


    /**
     * section products api
     */
    @GET("section/products")
    suspend fun sectionProducts(
        @Query("sectionId") sectionId: Int?
    ): NetworkProductInfo
}