package com.ahmed.abdallah.moviestaskapp.data.repository

import androidx.paging.PagingData
import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.data.model.response.KeyWord
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun getMovies(query: String?): Flow<PagingData<Movie>>
    suspend fun getKeywords(query: String): Flow<PagingData<KeyWord>>
    suspend fun getMovie(id: String): Result<Movie>
}