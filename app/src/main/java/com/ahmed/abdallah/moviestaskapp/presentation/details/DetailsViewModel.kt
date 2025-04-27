package com.ahmed.abdallah.moviestaskapp.presentation.details

import androidx.lifecycle.viewModelScope
import com.ahmed.abdallah.moviestaskapp.base.Result
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.data.model.response.mapToUI
import com.ahmed.abdallah.moviestaskapp.data.repository.IMovieRepository
import com.fawry.deliveryapp.base.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: IMovieRepository
) : BaseViewModel<DetailsContactor.State, DetailsContactor.Event, DetailsContactor.Action>(
    initialState = DetailsContactor.State()
) {


    override fun handleAction(action: DetailsContactor.Action) {
        when (action) {
            is DetailsContactor.Action.GetMovie -> {
                setState { copy(isLoading = true) }
                getMovie(action.id)
            }

        }
    }

    private fun getMovie(id: String) {
        viewModelScope.launch {
            val movieResult = movieRepository.getMovie(id)

            setState { copy(isLoading = false) }
            setEvent { DetailsContactor.Event.ShowError("movieResult.error") }

            when (movieResult) {
                is Result.Failure -> {
                    setEvent { DetailsContactor.Event.ShowError(movieResult.error) }
                }

                is Result.Success<Movie> -> setState { copy(movie = movieResult.data.mapToUI()) }
            }

        }
    }

}
