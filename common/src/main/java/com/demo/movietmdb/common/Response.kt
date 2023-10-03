package com.demo.movietmdb.common


sealed class Response<out R> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
