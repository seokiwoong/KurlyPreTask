package com.kt.naviagent.network

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class KtNetworkDataSourceTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var retrofitkt: Retrofitkt


    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun ktSectionInfoTest() = runBlocking {
        val sectionInfo = retrofitkt.getSectionInfo(1)
        Assert.assertNotEquals(sectionInfo.data.size , 0)
    }

    @Test
    fun ktSectionProductTest() = runBlocking {
        val sectionInfo = retrofitkt.getSectionProductInfo(1)
        Assert.assertNotEquals(sectionInfo.data.size , 0)
    }

}
