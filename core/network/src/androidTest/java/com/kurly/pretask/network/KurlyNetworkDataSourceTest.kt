package com.kurly.pretask.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kurly.android.mockserver.core.FileProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
internal class KurlyNetworkDataSourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("mock") lateinit var okhttpCallFactory: dagger.Lazy<Call.Factory>

    @Inject
    lateinit var json: Json

    lateinit var api : RetrofitKurlyNetworkApi

    @Before
    fun init() {
        hiltRule.inject()
        api = Retrofit.Builder()
            .baseUrl("https://kurly.com/")
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitKurlyNetworkApi::class.java)
    }

    @Test
    fun `KurlySectionInfoTest`() = runBlocking {
        val sectionInfo = api.sections(1)
        println( sectionInfo.data.size)
    }
}
