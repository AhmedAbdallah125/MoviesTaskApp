package com.ahmed.abdallah.moviestaskapp.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.ahmed.abdallah.moviestaskapp.data.model.response.Genres
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.presentation.components.CustomToastManager
import com.ahmed.abdallah.moviestaskapp.presentation.components.FilterChips
import com.ahmed.abdallah.moviestaskapp.presentation.components.Loader
import com.ahmed.abdallah.moviestaskapp.ui.theme.CoolGray
import com.ahmed.abdallah.moviestaskapp.ui.theme.OliveGreen
import com.ahmed.abdallah.moviestaskapp.ui.theme.PrimaryColor
import com.ahmed.abdallah.moviestaskapp.ui.theme.SecondaryColor
import com.ahmed.abdallah.moviestaskapp.ui.theme.Typography

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: DetailsViewModel,
    navController: NavHostController
) {
    val state = viewModel.uiState.collectAsState().value

    LaunchedEffect(id) {
        viewModel.handleAction(DetailsContactor.Action.GetMovie(id))
    }

    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is DetailsContactor.Event.ShowError -> context.let {
                    CustomToastManager(it).showToast(event.error)
                }
            }

        }
    }
    DetailsScreenContent(
        modifier = modifier,
        state = state,
        onBack = { navController.popBackStack() }
    )
}

@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    state: DetailsContactor.State,
    onBack: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = PrimaryColor)
    ) {


        Column(
            modifier = modifier
                .wrapContentSize()
                .background(color = PrimaryColor)
                .verticalScroll(rememberScrollState())
        ) {

            if (state.isLoading)
                Loader(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    show = true,
                    size = 300
                )
            else {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = state.movie?.posterPath,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    loading = { Loader(show = true, size = 100) }
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    itemVerticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                        maxLines = 1,
                        text = state.movie?.title ?: "",
                        style = Typography.headlineMedium.copy(color = Color.White)
                    )

                    Text(
                        modifier = Modifier.padding(start = 8.dp, end = 4.dp, top = 4.dp),
                        maxLines = 2,

                        text = "(${state.movie?.releaseDate})",
                        style = Typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.5f))
                    )
                }

                state.movie?.genres?.let { FilterChips(items = it) }

                HorizontalDivider(
                    color = SecondaryColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    thickness = 2.dp
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(verticalArrangement = Arrangement.Center) {


                        Text(
                            text = "Vote",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = Typography.titleMedium.copy(color = OliveGreen)
                        )

                        Text(
                            text = state.movie?.voteCount?.toString() ?: "",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = Typography.titleMedium.copy(color = CoolGray)
                        )
                    }

                    Column(verticalArrangement = Arrangement.Center) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = OliveGreen,
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = ("${state.movie?.voteAverage?.toInt()} /10"),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = Typography.titleMedium.copy(color = CoolGray)
                        )
                    }
                }

                HorizontalDivider(
                    color = SecondaryColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    thickness = 2.dp
                )

                Text(
                    text = state.movie?.overview ?: "",
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    style = Typography.bodyMedium.copy(color = CoolGray)
                )

            }
        }

        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = onBack
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopStart),
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "",
                tint = OliveGreen
            )
        }

    }
}

@Preview(widthDp = 500)
@Composable
private fun DetailsScreenContentPrev() {
    val mockMovie = Movie(
        genres = listOf(
            Genres(id = 28, name = "Action"),
            Genres(id = 35, name = "Comedy")
        ),
        imdbId = "tt9876543",
        adult = false,
        backdropPath = "/mock_backdrop.jpg",
        id = 67890,
        originalLanguage = "en",
        originalTitle = "Mock Original Title",
        overview = "This is a mock overview of a thrilling action-comedy movie.",
        popularity = 75.3,
        posterPath = "/mock_poster.jpg",
        releaseDate = "2025-12-15",
        title = "Mock Movie Title",
        video = false,
        voteAverage = 7.5,
        voteCount = 845
    )

    val state = DetailsContactor.State(movie = mockMovie)

    DetailsScreenContent(state = state, onBack = {})

}




