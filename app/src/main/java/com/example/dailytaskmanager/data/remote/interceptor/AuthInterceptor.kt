package com.example.dailytaskmanager.data.remote.interceptor

import com.example.dailytaskmanager.data.local.TokenDataStore
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val tokenDataStore: TokenDataStore
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Your implementation
    }
}