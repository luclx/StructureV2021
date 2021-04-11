package com.luclx.structure2021.data

import android.util.Log
import com.luclx.structure2021.base.BaseError
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ResponseHandler {
    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.Success(data)
    }

    fun <T> handleError(e: Throwable): Resource<T> {
        Log.e("API", e.localizedMessage ?: e.message ?: "")
        Log.e("API", (e is HttpException).toString())
        return when (e) {
            is UnknownHostException -> Resource.Error(
                BaseError(
                    Int.MAX_VALUE,
                    "There is a problem establishing a connection to the server. Please try again."
                ),
                null
            )
            is HttpException -> Resource.Error(
                BaseError(
                    e.code(),
                    getErrorMessage(e.response()?.errorBody()?.string(), e.code())
                ),
                null
            )
            is SocketTimeoutException -> Resource.Error(
                BaseError(
                    -1,
                    e.localizedMessage ?: e.message ?: ""
                ),
                null
            )
            else -> Resource.Error(
                BaseError(
                    Int.MAX_VALUE,
                    e.localizedMessage ?: e.message ?: ""
                ),
                null
            )
        }
    }

    private fun getErrorMessage(errorString: String? = null, code: Int): String {
        Log.e("API", "original errorMsg = $errorString")
        var errorMsg = ""
        errorString?.let { responseString ->
            try {
                if (responseString.isNotBlank()) {
                    val type = Types.newParameterizedType(ErrorBody::class.java)
                    val adapter = Moshi.Builder().build().adapter<ErrorBody>(type)
                    val body = adapter.fromJson(responseString)
                    errorMsg = body?.message ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (errorMsg.isBlank()) {
            errorMsg = when (code) {
                -1 -> "Timeout"
                401 -> "Unauthorised"
                404 -> "Not found"
                500 -> "Internal server"
                else -> "Something went wrong"
            }
        }
        Log.e("API", "return errorMsg = $errorMsg")
        return errorMsg
    }
}
