package com.ahmed.abdallah.moviestaskapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ahmed.abdallah.moviestaskapp.data.model.response.Genres
import com.ahmed.abdallah.moviestaskapp.ui.theme.SecondaryColor
import com.ahmed.abdallah.moviestaskapp.ui.theme.Typography

@Composable
fun FilterChips(modifier: Modifier = Modifier, items: List<Genres>) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {


        FlowRow(
            maxItemsInEachRow = 20,
            horizontalArrangement = Arrangement
                .spacedBy(
                    space = 6.dp,
                    alignment = Alignment.Start
                ),
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items.forEach { item ->
                SuggestionChip(
                    interactionSource = NoRippleInteractionSource(),
                    onClick = {},
                    border = BorderStroke(width = 1.dp, color = SecondaryColor),
                    shape = RoundedCornerShape(16.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(containerColor = Color.Transparent),
                    label = {
                        Text(
                            text = item.name ?: "",
                            style = Typography.labelSmall.copy(
                                color = Color.White
                            )
                        )
                    })
            }
        }
    }
}