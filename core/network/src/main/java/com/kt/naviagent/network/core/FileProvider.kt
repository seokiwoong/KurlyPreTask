package com.kt.naviagent.network.core

interface FileProvider {
    fun getJsonFromAsset(filePath: String): String?
}
