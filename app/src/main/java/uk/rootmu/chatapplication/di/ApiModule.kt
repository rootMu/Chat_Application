package uk.rootmu.chatapplication.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.rootmu.chatapplication.data.remote.ApiService

val apiModule = module {

    val BASE_URL = "https://api.openai.com/v1/"
    val token = "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"


    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single { OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token").build()

            chain.proceed(newRequest)
        }.build()
    }

    single { Retrofit.Builder().client(get()).baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build().create(ApiService::class.java) }

}
