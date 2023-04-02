package com.leandrolcd.comercializadoraayl.login.ui.states

sealed class UiStatus<T>() {

    class Loading<T>() : UiStatus<T>()
    class Login<T>() : UiStatus<T>()
    class Error<T>(val message: String?) : UiStatus<T>()
    class Success<T>(val data: T) : UiStatus<T>()
}
