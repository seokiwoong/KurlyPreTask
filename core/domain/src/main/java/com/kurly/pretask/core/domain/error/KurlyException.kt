package com.kt.naviagent.core.domain.error

import com.kt.naviagent.network.model.NetworkResponseError
import kotlinx.serialization.json.Json
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

data class ktException(
    val code: String,
    override val message: String
) : Throwable()

object ktErrorCode {
    const val UNKNOWN_ERROR = "-1"
    const val NETWORK_ERROR = "-999"
    const val NETWORK_TIMEOUT = "-9999"
}

fun Throwable.toktException(): ktException {
    val exception = when (this) {
        is ConnectException -> ktException(
            ktErrorCode.NETWORK_ERROR, this.toString()
        )

        is SocketTimeoutException -> ktException(
            ktErrorCode.NETWORK_TIMEOUT, this.toString()
        )

        is IOException -> ktException(
            ktErrorCode.NETWORK_ERROR, this.toString()
        )

        is HttpException -> if (this.code() in 400..599) {
            try {
                response()?.errorBody()?.string()?.let {
                    val networkResponseError = Json.decodeFromString<NetworkResponseError>(it)
                    ktException(
                        networkResponseError.code.toString(),
                        networkResponseError.msg,
                    )
                } ?: ktException(ktErrorCode.UNKNOWN_ERROR, toString())
            } catch (e: Exception) {
                ktException(ktErrorCode.UNKNOWN_ERROR, e.toString())
            }
        } else {
            ktException(ktErrorCode.UNKNOWN_ERROR, toString())
        }

        else -> ktException(ktErrorCode.UNKNOWN_ERROR, toString())
    }
    return exception
}