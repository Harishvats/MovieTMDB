package com.demo.movietmdb.common

sealed class ViewState<out R> {
    data class SuccessState<T>(val data: T) : ViewState<T>()
    data class ErrorState(val message: String) : ViewState<Nothing>()
    object LoadingState : ViewState<Nothing>()
}