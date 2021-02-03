package com.jutter.sharerecipes.server

import android.content.Context
import com.jutter.sharerecipes.extensions.getToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var request: Request = original

        request = if (context.getToken().isNotEmpty()) {
            original.newBuilder()
                    .header("Authorization", "Bearer " + context.getToken())
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()
        } else {
            original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()
        }

        return chain.proceed(request)
    }
}