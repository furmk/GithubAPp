package com.ingenious.githubapp.data.source.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiTokenInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithToken = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(requestWithToken)
    }
}
