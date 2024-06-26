package com.kurly.pretask.network

import com.kurly.pretask.network.model.NetworkProductInfo
import com.kurly.pretask.network.model.NetworkSectionInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitKurlyNetworkApi {
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