package com.ahmed.abdallah.moviestaskapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed.abdallah.moviestaskapp.ui.theme.Gray300
import com.ahmed.abdallah.moviestaskapp.ui.theme.Typography

@Composable
fun EmptySearchScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.Info,
            modifier = Modifier.size(120.dp),
            contentDescription = "image description",
            tint = Gray300
        )
        Text(
            text = "There is no data",
            style = Typography.titleLarge,
            color = Gray300,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun EmptyScreenPrev() {
    EmptySearchScreen(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    )
}