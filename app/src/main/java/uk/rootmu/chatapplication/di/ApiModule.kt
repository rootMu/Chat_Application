package uk.rootmu.chatapplication.di

import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.rootmu.chatapplication.data.remote.ApiService

val apiModule = module {

    val BASE_URL = "https://api.openai.com/v1/"
    val token = "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"

    single { OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request =
            chain.request().newBuilder().addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $token").build()
        chain.proceed(newRequest)
    }.build() }

    single { Retrofit.Builder().client(get()).baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java) }

}
