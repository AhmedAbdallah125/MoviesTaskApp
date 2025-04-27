package com.ahmed.abdallah.moviestaskapp.presentation.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ahmed.abdallah.moviestaskapp.data.model.response.mapToUI
import com.ahmed.abdallah.moviestaskapp.data.repository.IMovieRepository
import com.fawry.deliveryapp.base.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
) : BaseViewModel<HomeContractor.State, HomeContractor.Event, HomeContractor.Action>(initialState = HomeContractor.State()) {

    init {
        handleAction(HomeContractor.Action.GetAllMovies())
    }

    override fun handleAction(action: HomeContractor.Action) {
        when (action) {
            is HomeContractor.Action.GetAllMovies -> {
                setState { copy(isLoading = true, keyWord = emptyFlow()) }
                getAllMovies(action.search)
            }

            is HomeContractor.Action.UpdateSearchValue -> {
                setState { copy(searchValue = action.value, keyWord = emptyFlow()) }
                if (action.value.isNullOrEmpty())
                    getAllMovies(null)
            }

            is HomeContractor.Action.GoToDetails -> setEvent {
                HomeContractor.Event.GoToDetails(
                    action.movieId
                )
            }

            is HomeContractor.Action.GetKeywords -> getKeywords(action.query)
        }
    }

    private fun getKeywords(query: String) {
        viewModelScope.launch {
            val keywords = movieRepository.getKeywords(query).cachedIn(viewModelScope)
            setState {
                copy(
                    keyWord = keywords
                )
            }
        }
    }

    private fun getAllMovies(search: String?) {
        viewModelScope.launch {
            val movies = movieRepository.getMovies(search).map { pagingData ->
                pagingData.map {
                    it.mapToUI()
                }
            }.cachedIn(viewModelScope)
            setState {
                copy(
                    isLoading = false,
                    movies = movies
                )
            }
        }
    }
}

