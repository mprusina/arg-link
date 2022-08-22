package com.rss.arglink.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rss.arglink.BuildConfig
import com.rss.arglink.data.api.LinkItemService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api-sandbox.argyle.com/v1/"

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): LinkItemService {
        return retrofit.create(LinkItemService::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic ${BuildConfig.AUTH_TOKEN}")
                .build()
            chain.proceed(newRequest)
        }.build()
    }
}