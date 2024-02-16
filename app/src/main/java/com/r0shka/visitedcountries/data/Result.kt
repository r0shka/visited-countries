package com.r0shka.visitedcountries.data

sealed class Result<out T> {
    data class Success<T>(val value: T): Result<T>()
    data class Error(val error: Throwable): Result<Nothing>()
}