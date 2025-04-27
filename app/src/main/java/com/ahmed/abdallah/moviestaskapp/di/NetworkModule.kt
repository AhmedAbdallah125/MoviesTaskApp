package com.ahmed.abdallah.moviestaskapp.di

import android.util.Log
import com.ahmed.abdallah.moviestaskapp.BuildConfig
import com.ahmed.abdallah.moviestaskapp.data.remote.MainRemoteSource
import com.ahmed.abdallah.moviestaskapp.data.repository.IMovieRepository
import com.ahmed.abdallah.moviestaskapp.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @Named("BEARER_TOKEN")
    fun provideBearerToken(): String = BuildConfig.BEARER_TOKEN

    @Provides
    @Singleton
    fun provideKtorClient(@Named("BEARER_TOKEN") bearerToken: String): HttpClient {
        return HttpClient(Android) {

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HttpLogging:", message)
                    }

                }
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, bearerToken)
            }
        }
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        mainRemoteSource: MainRemoteSource
    ): IMovieRepository = MovieRepositoryImpl(mainRemoteSource)

}
