package com.example.androiddevchallenge

data class Result<T>(val status: Int, val data: T?, val msg: String?) {
    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val LOADING = 2

        fun <T> success(data: T) = Result(SUCCESS, data, null)
        fun <T> error(msg: String) = Result<T>(ERROR, null, msg)
        fun <T> loading() = Result<T>(LOADING, null, null)
    }
}