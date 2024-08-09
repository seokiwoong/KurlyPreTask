package com.kt.naviagent.core.domain.common

import android.util.Log
import com.kt.naviagent.core.domain.common.Result.Loading
import com.kt.naviagent.core.domain.common.Result.Success
import com.kt.naviagent.core.domain.common.Result.Error
import com.kt.naviagent.core.domain.error.ktException
import com.kt.naviagent.core.domain.error.toktException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: ktException) : Result<Nothing>
    data object Loading : Result<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Success(it)
        }
        .onStart { emit(Loading) }
        .catch {
            Log.e("Result", "Error in asResult", it)
            emit(Error(it.toktException()))
        }
}
