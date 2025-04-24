package com.ahmed.abdallah.moviestaskapp.base


sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()

    data class Failure(val error: String) : Result<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }


}

fun <T> Result<T>.getData(): T? {
    return when (this) {
        is Result.Success -> data
        is Result.Failure -> null
    }
}

