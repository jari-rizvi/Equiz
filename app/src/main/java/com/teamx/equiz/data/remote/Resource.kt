package com.teamx.equiz.data.remote



import androidx.annotation.Keep

@Keep
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        AUTH,
        NOTVERIFY,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> unAuth(message: String, data: T? = null): Resource<T> {
            return Resource(Status.AUTH, data, message)
        }

        fun <T> notVerify(message: String, data: T? = null): Resource<T> {
            return Resource(Status.NOTVERIFY, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}