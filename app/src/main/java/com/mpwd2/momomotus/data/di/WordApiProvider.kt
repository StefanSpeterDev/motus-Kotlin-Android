package com.mpwd2.momomotus.data.di

import com.mpwd2.momomotus.data.dataSources.remote.api.WordApi
import com.mpwd2.momomotus.data.dataSources.remote.api.WordApi.Companion.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WordApiProvider {

    @Provides
    @Singleton
    fun provideAuthInterceptorOkHttpClient() : OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideWordApiService(retrofit: Retrofit) : WordApi.Service {
        return retrofit.create(WordApi.Service::class.java)
    }

}