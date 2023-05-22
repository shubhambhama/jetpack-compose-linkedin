package com.clone.linkedin.linkedin.data

import androidx.annotation.Keep

@Keep
sealed class Response<T>(data: T? = null, message: String? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Failed<T>(data: T, message: String) : Response<T>(data, message)
}