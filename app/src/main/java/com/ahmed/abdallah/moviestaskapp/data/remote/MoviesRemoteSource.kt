package com.ahmed.abdallah.moviestaskapp.data.remote

import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.base.Result.*
import com.ahmed.abdallah.moviestaskapp.data.model.response.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Named
import javax.inject.Singleton

private val GET_ALL_MOVIES = "albums"


@Singleton
class MoviesRemoteSource(
    private val httpClient: HttpClient,
    @Named("BaseUrl") private val baseUrl: String
) {

    suspend fun getAllMovies(): Result<MoviesResponse> {
        return try {
            Success(httpClient.get("$baseUrl$GET_ALL_MOVIES").body())
        } catch (ex: Exception) {
            Failure(error = ex.message ?: "UN Known Error")
        }
    }


}