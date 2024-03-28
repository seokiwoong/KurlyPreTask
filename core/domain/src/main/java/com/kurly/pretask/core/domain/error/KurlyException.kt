package com.kurly.pretask.core.domain.error

import com.kurly.pretask.network.model.NetworkResponseError
import kotlinx.serialization.json.Json
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

data class KurlyException(
    val code: String,
    override val message: String
) : Throwable()

object KurlyErrorCode {
    const val UNKNOWN_ERROR = "-1"
    const val NETWORK_ERROR = "-999"
    const val NETWORK_TIMEOUT = "-9999"
}

fun Throwable.toKurlyException(): KurlyException {
    val exception = when (this) {
        is ConnectException -> KurlyException(
            KurlyErrorCode.NETWORK_ERROR, this.toString()
        )

        is SocketTimeoutException -> KurlyException(
            KurlyErrorCode.NETWORK_TIMEOUT, this.toString()
        )

        is IOException -> KurlyException(
            KurlyErrorCode.NETWORK_ERROR, this.toString()
        )

        is HttpException -> if (this.code() in 400..599) {
            try {
                response()?.errorBody()?.string()?.let {
                    val networkResponseError = Json.decodeFromString<NetworkResponseError>(it)
                    KurlyException(
                        networkResponseError.code.toString(),
                        networkResponseError.msg,
                    )
                } ?: KurlyException(KurlyErrorCode.UNKNOWN_ERROR, toString())
            } catch (e: Exception) {
                KurlyException(KurlyErrorCode.UNKNOWN_ERROR, e.toString())
            }
        } else {
            KurlyException(KurlyErrorCode.UNKNOWN_ERROR, toString())
        }

        else -> KurlyException(KurlyErrorCode.UNKNOWN_ERROR, toString())
    }
    return exception
}