package com.ahmed.abdallah.moviestaskapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.data.model.response.KeyWord
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.data.remote.KeywordDataSource
import com.ahmed.abdallah.moviestaskapp.data.remote.MoviesDataSource
import com.ahmed.abdallah.moviestaskapp.data.remote.MainRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remoteSource: MainRemoteSource) :
    IMovieRepository {

    override suspend fun getMovies(query: String?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                MoviesDataSource { page ->
                    if (query.isNullOrEmpty())
                        remoteSource.getAllMovies(page)
                    else
                        remoteSource.getMoviesForKeyword(page, query)
                }
            }
        ).flow
    }

    override suspend fun getKeywords(query: String): Flow<PagingData<KeyWord>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                KeywordDataSource { page ->
                    remoteSource.getKeyword(page, query)
                }
            }
        ).flow
    }

    override suspend fun getMovie(id: String): Result<Movie> {
        return remoteSource.getMovieById(id)
    }
}