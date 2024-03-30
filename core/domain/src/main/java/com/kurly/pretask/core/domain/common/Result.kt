package com.kurly.pretask.core.domain.common

import android.util.Log
import com.kurly.pretask.core.domain.common.Result.Loading
import com.kurly.pretask.core.domain.common.Result.Success
import com.kurly.pretask.core.domain.common.Result.Error
import com.kurly.pretask.core.domain.error.KurlyException
import com.kurly.pretask.core.domain.error.toKurlyException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: KurlyException) : Result<Nothing>
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
            emit(Error(it.toKurlyException()))
        }
}
