package com.example.dailytaskmanager.data.remote.interceptor

import com.example.dailytaskmanager.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

object ApiLoggingInterceptor {

    fun create(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {

            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

            redactHeader("Authorization")
            redactHeader("Cookie")
        }
    }
}