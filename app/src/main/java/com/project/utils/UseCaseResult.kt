package com.project.utils

/**
 * Created by Priyanka on 16/04/2020
 */
sealed class UseCaseResult<out T : Any> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Exception(val exception: Throwable) : UseCaseResult<Nothing>()
}