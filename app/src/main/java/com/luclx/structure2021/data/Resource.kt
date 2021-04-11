package com.luclx.structure2021.data

import com.luclx.structure2021.base.BaseError

sealed class Resource<out T> {
    /**
     * Loading....
     */
    object Loading : Resource<Nothing>()

    /**
     * Successful API connection then returns data
     * Should be returned to MainThread
     */
    data class Success<T>(val data: T) : Resource<T>()

    /**
     * API connection error
     */
    data class Error<T>(val error: BaseError? = null, val data: T? = null) : Resource<T>()
}