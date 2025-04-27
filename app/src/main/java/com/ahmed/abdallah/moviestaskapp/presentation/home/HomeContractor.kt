package com.ahmed.abdallah.moviestaskapp.presentation.home

import androidx.paging.PagingData
import com.ahmed.abdallah.moviestaskapp.data.model.response.KeyWord
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.fawry.deliveryapp.base.presentation.ViewAction
import com.fawry.deliveryapp.base.presentation.ViewEvent
import com.fawry.deliveryapp.base.presentation.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeContractor {
    data class State(
        val isLoading: Boolean = false,
        val movies: Flow<PagingData<Movie>> = MutableStateFlow(value = PagingData.empty()),
        val keyWord: Flow<PagingData<KeyWord>> = MutableStateFlow(value = PagingData.empty()),
        val searchValue: String? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class ShowError(val error: String) : Event()
        data class GoToDetails(val id: String) : Event()

    }

    sealed class Action : ViewAction {
        data class GetAllMovies(val search: String? = null) : Action()
        data class UpdateSearchValue(val value: String? = null) : Action()
        data class GoToDetails(val movieId: String) : Action()
        data class GetKeywords(val query: String) : Action()

    }
}