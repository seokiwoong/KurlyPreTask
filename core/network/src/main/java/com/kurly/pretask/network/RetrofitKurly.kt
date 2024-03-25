package com.kurly.pretask.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kurly.pretask.network.model.NetworkProductInfo
import com.kurly.pretask.network.model.NetworkSectionInfo
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
internal class RetrofitKurly @Inject constructor(
    json: Json,
    @Named("mock") okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : KurlyNetworkDataSource {

    private val retrofitClient =
        Retrofit.Builder()
            .baseUrl("https://kurly.com/")
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitKurlyNetworkApi::class.java)

    override suspend fun getSectionInfo(page: Int): NetworkSectionInfo =
        retrofitClient.sections(page)

    override suspend fun getSectionProductInfo(sectionId: Int): NetworkProductInfo  =
        retrofitClient.sectionProducts(sectionId)

}