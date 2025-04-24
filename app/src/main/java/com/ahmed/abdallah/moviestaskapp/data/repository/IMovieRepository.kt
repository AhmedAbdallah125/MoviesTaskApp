package com.ahmed.abdallah.moviestaskapp.data.repository

import com.ahmed.abdallah.moviestaskapp.data.model.response.MoviesResponse

interface IMovieRepository {
    suspend fun getMovies(): MoviesResponse
}