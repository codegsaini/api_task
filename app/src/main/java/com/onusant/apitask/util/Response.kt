package com.onusant.apitask.util

sealed class Response<out T: Any> {

    data class Success<out T: Any>(val data: T) : Response<T>()

    data class Failure(val error: Error) : Response<Nothing>()
}