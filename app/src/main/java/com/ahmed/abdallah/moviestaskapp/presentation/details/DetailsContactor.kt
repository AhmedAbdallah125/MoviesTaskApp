package com.ahmed.abdallah.moviestaskapp.presentation.details

import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.fawry.deliveryapp.base.presentation.ViewAction
import com.fawry.deliveryapp.base.presentation.ViewEvent
import com.fawry.deliveryapp.base.presentation.ViewState

class DetailsContactor {
    data class State(
        val isLoading: Boolean = false,
        val movie: Movie? = null,

        ) : ViewState

    sealed class Event : ViewEvent {
        data class ShowError(val error: String) : Event()
    }

    sealed class Action : ViewAction {
        data class GetMovie(val id: String) : Action()
    }
}