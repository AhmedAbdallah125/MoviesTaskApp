package com.ahmed.abdallah.moviestaskapp.data.remote

import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.base.Result.*
import com.ahmed.abdallah.moviestaskapp.data.model.response.KeyWordResponse
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.data.model.response.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private const val GET_ALL_MOVIES = "discover/movie"
private const val GET_SEARCHED_MOVIES = "search/movie"
private const val GET_MOVIE = "movie"
private const val GET_KEYWORDS = "search/keyword"


@Singleton
class MainRemoteSource @Inject constructor(
    private val httpClient: HttpClient,
    @Named("BASE_URL") private val baseUrl: String
) {

    suspend fun getAllMovies(page: Int): Result<MoviesResponse> {
        return try {
            Success(httpClient.get("$baseUrl$GET_ALL_MOVIES") {
                parameter("include_adult", false)
                parameter("include_video", false)
                parameter("language", "en-US")
                parameter("page", page)
                parameter("sort_by", "popularity.desc")
            }.body())
        } catch (ex: Exception) {
            Failure(error = ex.message ?: "UN Known Error")
        }
    }

    suspend fun getMoviesForKeyword(page: Int, keyword: String): Result<MoviesResponse> {
        return try {
            Success(httpClient.get("$baseUrl$GET_SEARCHED_MOVIES") {
                parameter("include_adult", false)
                parameter("include_video", false)
                parameter("language", "en-US")
                parameter("page", page)
                parameter("query", keyword)
                parameter("sort_by", "popularity.desc")
            }.body())
        } catch (ex: Exception) {
            Failure(error = ex.message ?: "UN Known Error")
        }
    }


    suspend fun getMovieById(id: String): Result<Movie> {
        return try {
            Success(httpClient.get("$baseUrl$GET_MOVIE/$id").body())
        } catch (ex: Exception) {
            Failure(error = ex.message ?: "UN Known Error")
        }
    }

    suspend fun getKeyword(page: Int, query: String): Result<KeyWordResponse> {
        return try {
            Success(httpClient.get("$baseUrl$GET_KEYWORDS") {
                parameter("page", page)
                parameter("query", query)

            }.body())
        } catch (ex: Exception) {
            Failure(error = ex.message ?: "UN Known Error")
        }
    }


}