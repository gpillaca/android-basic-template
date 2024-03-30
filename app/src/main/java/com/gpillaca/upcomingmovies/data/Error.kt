package com.gpillaca.upcomingmovies.data

import retrofit2.HttpException
import okio.IOException


sealed interface Error {
    class Server(val code: Int): Error
    object Connectivity: Error
    class Unknown(val message: String): Error
}

fun Throwable.toError() = when(this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}

inline fun <T> tryCall(action: () -> T): Error? = try {
    action()
    null
} catch (e: Exception) {
    e.toError()
}
