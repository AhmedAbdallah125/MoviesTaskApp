package com.ahmed.abdallah.moviestaskapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.data.model.response.MoviesResponse

class MoviesDataSource(
    private val apiCall: suspend (page: Int) -> Result<MoviesResponse>
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1
            when (val response = apiCall(currentPage)) {
                is Result.Success -> {
                    val data = response.data.results ?: emptyList()
                    LoadResult.Page(
                        data = data,
                        prevKey = if (currentPage <= 1) null else currentPage - 1,
                        nextKey = if (data.isEmpty()) null else currentPage + 1
                    )
                }

                is Result.Failure -> {
                    LoadResult.Error(
                        Exception(response.error)
                    )
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}