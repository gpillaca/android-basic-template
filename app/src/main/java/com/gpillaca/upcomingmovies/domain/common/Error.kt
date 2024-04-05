package com.gpillaca.upcomingmovies.domain.common

import retrofit2.HttpException
import okio.IOException

sealed interface Error {
    data class Server(val code: Int): Error
    data object Connectivity: Error
    data class Unknown(val message: String): Error
}

fun Throwable.toError() = when(this) {
    is IOException -> Error.Connectivity
    is HttpException -> Error.Server(code())
    else -> Error.Unknown(message ?: "")
}

@JvmName("tryCatch")
inline fun <T> catch(function: () -> T): Either<Error, T> = try {
    function().right()
} catch (exception: Exception) {
    exception.toError().left()
}
