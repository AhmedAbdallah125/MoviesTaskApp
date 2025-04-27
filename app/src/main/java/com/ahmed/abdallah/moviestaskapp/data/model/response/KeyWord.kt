package com.ahmed.abdallah.moviestaskapp.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class KeyWordResponse(
    var page: Int? = null,
    var results: List<KeyWord>? = emptyList(),
    var totalPages: Int? = null,
    var totalResults: Int? = null
)

@Serializable
data class KeyWord(
    var id: Int? = null,
    var name: String? = null

)