package com.demo.movietmdb.di

import com.demo.movietmdb.BuildConfig
import com.demo.movietmdb.data.api.TMDBService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults= true
    }
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideTMDBService(retrofit: Retrofit): TMDBService {
        return retrofit.create(TMDBService::class.java)
    }
}