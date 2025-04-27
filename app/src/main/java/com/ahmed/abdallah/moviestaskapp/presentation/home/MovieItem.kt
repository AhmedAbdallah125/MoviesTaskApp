package com.ahmed.abdallah.moviestaskapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ahmed.abdallah.moviestaskapp.data.model.response.Movie
import com.ahmed.abdallah.moviestaskapp.presentation.components.Loader
import com.ahmed.abdallah.moviestaskapp.ui.theme.PrimaryColor
import com.ahmed.abdallah.moviestaskapp.ui.theme.CoolGray
import com.ahmed.abdallah.moviestaskapp.ui.theme.Gray300
import com.ahmed.abdallah.moviestaskapp.ui.theme.OliveGreen
import com.ahmed.abdallah.moviestaskapp.ui.theme.Typography

@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: Movie, onClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = PrimaryColor)
    ) {
        Column(
            modifier = modifier
                .height(260.dp)
                .clickable { onClick(movie.id?.toString() ?: "0") }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.height(180.dp),
                model = movie.posterPath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = { Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Gray300)) },
                loading = { Loader(show = true, size = 100) }
            )

            Text(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                maxLines = 1,
                text = movie.title ?: "",
                style = Typography.labelSmall.copy(color = Color.White)
            )
            Text(
                text = movie.overview ?: "",
                modifier = Modifier.padding(start = 8.dp),
                style = Typography.labelSmall.copy(color = CoolGray),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            RatingBar(
                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                rating = (movie.voteAverage ?: 2.0) / 2.0
            )
        }
    }
}

@Preview(widthDp = 500)
@Composable
private fun MovieItemPrev() {
    MovieItem(
        movie = Movie(
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
        )
    ) {

    }

}

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double,
    stars: Int = 5
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 1..stars) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= rating) OliveGreen else Color.White,
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}


