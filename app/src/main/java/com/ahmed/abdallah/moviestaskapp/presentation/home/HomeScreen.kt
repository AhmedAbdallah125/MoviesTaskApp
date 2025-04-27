package com.ahmed.abdallah.moviestaskapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ahmed.abdallah.moviestaskapp.data.model.response.KeyWord
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.naviagtion.MainNavigation
import com.ahmed.abdallah.moviestaskapp.presentation.components.EmptySearchScreen
import com.ahmed.abdallah.moviestaskapp.presentation.components.Loader
import com.ahmed.abdallah.moviestaskapp.ui.theme.Gray300
import com.ahmed.abdallah.moviestaskapp.ui.theme.SecondaryColor
import com.ahmed.abdallah.moviestaskapp.ui.theme.CoolGray
import com.ahmed.abdallah.moviestaskapp.ui.theme.OliveGreen
import com.ahmed.abdallah.moviestaskapp.ui.theme.Typography
/*import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition*/
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: HomeViewModel, navController: NavHostController,
) {
    val state = viewModel.uiState.collectAsState().value
    val lazyMoviePaging = state.movies.collectAsLazyPagingItems()
    val lazyKeywordPaging = state.keyWord.collectAsLazyPagingItems()

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeContractor.Event.GoToDetails -> {
                    navController.navigate(
                        "${
                            MainNavigation.Details.route
                        }/${event.id}"
                    )
                }

                is HomeContractor.Event.ShowError -> {}
            }
        }
    }
    HomeScreenContent(
        modifier = modifier, state = state,
        lazyPagingItem = lazyMoviePaging,
        lazyKeywordItems = lazyKeywordPaging,
        onMovieClick = { viewModel.handleAction(HomeContractor.Action.GoToDetails(it)) },
        onSearch = { viewModel.handleAction(HomeContractor.Action.GetAllMovies(it)) },
        onUpdateValue = {
            viewModel.handleAction(HomeContractor.Action.UpdateSearchValue(it))
            if ((it?.length ?: 0) >= 2)
                viewModel.handleAction(HomeContractor.Action.GetKeywords(it!!))
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeContractor.State,
    lazyPagingItem: LazyPagingItems<Movie>,
    lazyKeywordItems: LazyPagingItems<KeyWord>,
    onUpdateValue: (String?) -> Unit,
    onSearch: (String) -> Unit,
    onMovieClick: (String) -> Unit
) {


    Column(
        Modifier
            .fillMaxSize()
            .background(color = SecondaryColor)
    ) {

        BasicTextField(
            value = state.searchValue ?: "",
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Gray300,
                    shape = RoundedCornerShape(24.dp)
                ),
            onValueChange = onUpdateValue,
            enabled = true,
            readOnly = false,
            textStyle = TextStyle(color = White),
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onAny = {
                    if (((state.searchValue?.length) ?: 0) >= 2)
                        onSearch(state.searchValue!!)

                }
            ),
            singleLine = true,
            maxLines = 1,
            decorationBox =
                @Composable { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = state.searchValue ?: "",
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },
                        isError = false,
                        label = null,
                        placeholder = {
                            Text(
                                text = "Type to search...",
                                style = Typography.labelMedium.copy(color = CoolGray)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = CoolGray,
                                contentDescription = "search"
                            )
                        },
                        trailingIcon = if (!state.searchValue.isNullOrEmpty()) {
                            {
                                IconButton(onClick = {
                                    onUpdateValue("")
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        tint = CoolGray,
                                        contentDescription = null,
                                    )
                                }
                            }
                        } else null,
                        prefix = null,
                        suffix = null,
                        supportingText = null,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = CoolGray,
                            focusedContainerColor = Transparent,
                            unfocusedContainerColor = Transparent,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp),
                    )
                }, cursorBrush = SolidColor(OliveGreen)
        )




        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Transparent)
                .heightIn(max = (200.dp))
                .padding(horizontal = 16.dp),
            state = rememberLazyListState(),
        ) {
            items(
                lazyKeywordItems.itemCount,
                key = { lazyKeywordItems.peek(it)?.id ?: 0 }) { index ->
                lazyKeywordItems[index]?.let {
                    Text(
                        text = it.name ?: "",
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .clickable {
                                onUpdateValue(it.name)
                                onSearch(it.name ?: "")
                            },
                        style = Typography.labelLarge,
                        color = White
                    )
                }
            }

        }


        LazyVerticalStaggeredGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(
                lazyPagingItem.itemCount,
                key = { lazyPagingItem.peek(it)?.id ?: 0 }) { index ->
                lazyPagingItem[index]?.let { MovieItem(movie = it, onClick = onMovieClick) }
            }
            lazyPagingItem.apply {
                when {

                    lazyPagingItem.loadState.refresh is LoadState.Loading ->
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Loader(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp)
                                    .align(Alignment.CenterHorizontally),
                                show = true,
                                size = 250
                            )
                        }


                    (lazyPagingItem.loadState.refresh is LoadState.NotLoading && lazyPagingItem.itemCount < 1) ->
                        item(span = StaggeredGridItemSpan.FullLine) {
                            EmptySearchScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 64.dp)
                            )
                        }


                    loadState.refresh is LoadState.Error -> {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            retry()
                        }
                    }


                    loadState.append is LoadState.Loading -> {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Loader(size = 32, show = true)

                        }
                    }


                    loadState.append is LoadState.Error -> {
                        item(span = StaggeredGridItemSpan.FullLine) {
                            Text(text = "Error Append")
                        }
                    }
                }
            }
        }

    }


}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    val state = HomeContractor.State()

    val sampleData = listOf(
        Movie(
            adult = false,
            backdropPath = "/fTrQsdMS2MUw00RnzH0r3JWHhts.jpg",
            id = 1197306,
            originalLanguage = "en",
            originalTitle = "A Working Man",
            overview = "Levon Cade left behind a decorated military career in the black ops to live a simple life working construction. But when his boss's daughter, who is like family to him, is taken by human traffickers, his search to bring her home uncovers a world of corruption far greater than he ever could have imagined.",
            popularity = 798.7089,
            posterPath = "/xUkUZ8eOnrOnnJAfusZUqKYZiDu.jpg",
            releaseDate = "2025-03-26",
            title = "A Working Man",
            video = false,
            voteAverage = 6.307,
            voteCount = 429
        ),
        Movie(
            adult = false,
            backdropPath = "/fTrQsdMS2MUw00RnzH0r3JWHhts.jpg",
            id = 1197306,
            originalLanguage = "en",
            originalTitle = "A Working Man",
            overview = "Levon Cade left behind a decorated military career in the black ops to live a simple life working construction. But when his boss's daughter, who is like family to him, is taken by human traffickers, his search to bring her home uncovers a world of corruption far greater than he ever could have imagined.",
            popularity = 798.7089,
            posterPath = "/xUkUZ8eOnrOnnJAfusZUqKYZiDu.jpg",
            releaseDate = "2025-03-26",
            title = "A Working Man",
            video = false,
            voteAverage = 6.307,
            voteCount = 429
        ),
    )
    val pagingData = PagingData.from(
        sampleData,
        LoadStates(
            refresh = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false),
        )
    )

    val pagingKeyWordData = PagingData.from(
        listOf(KeyWord(name = "Working")),
        LoadStates(
            refresh = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false),
        )
    )

    val lazyPagingItems = flowOf(
        pagingData,
    ).collectAsLazyPagingItems()

    val lazyKeywordItems = flowOf(
        pagingKeyWordData,
    ).collectAsLazyPagingItems()


    HomeScreenContent(
        state = state, modifier = Modifier,
        lazyPagingItem = lazyPagingItems,
        lazyKeywordItems = lazyKeywordItems,
        onUpdateValue = {},
        onSearch = {},
        onMovieClick = {}
    )
}

