package com.example.dailytaskmanager.data.remote.interceptor

import com.example.dailytaskmanager.data.local.TokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val tokenDataStore: TokenDataStore
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.encodedPath

        val isAuthEndpoint = path == "/api/auth/login" ||
                path == "/api/auth/register"


        if (isAuthEndpoint) {
            return chain.proceed(chain.request())
        }


        val token = runBlocking {
            tokenDataStore.getToken().first()
        }

        val originalRequest = chain.request()


        if (token != null) {
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            return chain.proceed(newRequest)
        }

        return chain.proceed(originalRequest)
    }
}