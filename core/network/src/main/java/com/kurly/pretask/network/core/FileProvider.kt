package com.kurly.pretask.network.core

interface FileProvider {
    fun getJsonFromAsset(filePath: String): String?
}
