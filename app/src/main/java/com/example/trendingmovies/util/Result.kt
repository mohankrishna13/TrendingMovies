package com.example.trendingmovies.util

class Result<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?,message: String?): Result<T> {
            return Result(Status.SUCCESS, data, message)
        }

        fun <T> error(message: String?): Result<T> {
            return Result(Status.ERROR, null, message)
        }

    }

}

enum class Status {
    SUCCESS,
    ERROR
}
