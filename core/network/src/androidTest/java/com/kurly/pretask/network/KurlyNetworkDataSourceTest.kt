package com.kurly.pretask.network

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class KurlyNetworkDataSourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var retrofitKurly: RetrofitKurly


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun kurlySectionInfoTest() = runBlocking {
        val sectionInfo = retrofitKurly.getSectionInfo(1)
        Assert.assertNotEquals(sectionInfo.data.size , 0)
    }

    @Test
    fun kurlySectionProductTest() = runBlocking {
        val sectionInfo = retrofitKurly.getSectionProductInfo(1)
        Assert.assertNotEquals(sectionInfo.data.size , 0)
    }

}
