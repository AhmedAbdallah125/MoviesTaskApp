package com.ahmed.abdallah.moviestaskapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.data.remote.MoviesRemoteSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val remoteSource: MoviesRemoteSource) :
    IMovieRepository {
    override suspend fun getMovies():Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                ShipmentsDataSource { page, size ->
                    remoteSource.getCompletedShipments(page, size, search, state = state)
                }
            }
        ).flow
    }
}