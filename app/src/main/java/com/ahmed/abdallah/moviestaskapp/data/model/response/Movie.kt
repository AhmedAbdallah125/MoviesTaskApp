package com.ahmed.abdallah.moviestaskapp.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    var page: Int? = null,
    var results: List<Movie>? = emptyList(),
    var totalPages: Int? = null,
    var totalResults: Int? = null
)

@Serializable
data class Movie(
    var genres: List<Genres> = emptyList(),
    @SerialName("imdb_id")
    var imdbId: String? = null,
    val adult: Boolean?,
    @SerialName("backdrop_path") val backdropPath: String?,
    val id: Int?,
    @SerialName("original_language") val originalLanguage: String?,
    @SerialName("original_title") val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("release_date") val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerialName("vote_average") val voteAverage: Double?,
    @SerialName("vote_count") val voteCount: Int?
)

@Serializable
data class Genres(
    var id: Int? = null,
    var name: String? = null
)

const val BaseImagePath = "https://image.tmdb.org/t/p/original"
fun Movie.mapToUI() =
    this.copy(posterPath = BaseImagePath.plus(posterPath))