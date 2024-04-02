package com.gpillaca.upcomingmovies

sealed class Either<out ErrorType, out ResultType> {
    class Left<ErrorType>(val error: ErrorType) : Either<ErrorType, Nothing>()
    class Right<ResultType>(val value: ResultType) : Either<Nothing, ResultType>()

    inline fun <T> fold(ifLeft: (ErrorType) -> T, ifRight: (ResultType) -> T): T =
        when (this) {
            is Left -> ifLeft(error)
            is Right -> ifRight(value)
        }

    @JvmName("mapRight")
    fun <NewResultType> map(
        ifRight: (ResultType) -> NewResultType
    ): Either<ErrorType, NewResultType> {
        return when (this) {
            is Left -> this
            is Right -> Right(ifRight(this.value))
        }
    }

    suspend fun <NewResultType> mapSuspendable(ifRight: suspend (ResultType) -> NewResultType): Either<ErrorType, NewResultType> {
        return when (this) {
            is Left -> this
            is Right -> Right(ifRight(this.value))
        }
    }

    @JvmName("mapLeft")
    fun <NewErrorType> map(
        ifLeft: (ErrorType) -> NewErrorType
    ): Either<NewErrorType, ResultType> {
        return when (this) {
            is Left -> Left(ifLeft(this.error))
            is Right -> this
        }
    }

    suspend fun <NewErrorType, NewResultType> concat(
        function: suspend (ResultType) -> Either<NewErrorType, NewResultType>,
        errorTransformation: (error: ErrorType) -> Either<NewErrorType, Nothing>
    ): Either<NewErrorType, NewResultType> {
        return when (this) {
            is Right -> function(this.value)
            is Left -> errorTransformation(error)
        }
    }
}

inline fun <ErrorType, ResultType> Either<ErrorType, ResultType>.onLeft(function: (failure: ErrorType) -> Unit): Either<ErrorType, ResultType> =
    this.apply { if (this is Either.Left) function(error) }

inline fun <ErrorType, ResultType> Either<ErrorType, ResultType>.onRight(function: (success: ResultType) -> Unit): Either<ErrorType, ResultType> =
    this.apply { if (this is Either.Right) function(value) }

fun <ErrorType> ErrorType.left(): Either<ErrorType, Nothing> = Either.Left(this)

fun <ResultType> ResultType.right(): Either<Nothing, ResultType> = Either.Right(this)