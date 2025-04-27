package com.ahmed.abdallah.moviestaskapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ahmed.abdallah.moviestaskapp.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedPreloader(
    modifier: Modifier = Modifier,
    loader: Int = R.raw.loader,
    contentScale: ContentScale = ContentScale.Fit
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            loader
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier,
        contentScale = contentScale
    )
}


@Composable
fun Loader(
    modifier: Modifier = Modifier,
    size: Int = 46,
    show: Boolean
) {
    if (show)
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            AnimatedPreloader(
                modifier = Modifier
                    .size(size.dp)
                    .align(Alignment.Center)
            )
        }
}