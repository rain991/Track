package com.savenko.track.presentation.components.customComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.UiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.other.colors.parseColor

@Composable
fun CategorySettingsChip(
    category: CategoryEntity,
    borderColor: Color?,
    onSelect: (CategoryEntity) -> Unit
) {
    val databaseStringResourcesProvider = DatabaseStringResourcesProvider()
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .scale(0.9f),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = parseColor(hexColor = category.colorId),
            contentColor = Color.White,
            disabledContainerColor = parseColor(hexColor = category.colorId),
            disabledContentColor = Color.White
        ), border = if (borderColor != null) {
            BorderStroke((1.5).dp, borderColor)
        } else {
            null
        }
    ) {
        Box(modifier = Modifier
            .zIndex(1f)
            .wrapContentSize()) {
            Text(
                text = if (category.isDefault()) {
                    stringResource(
                        id = databaseStringResourcesProvider.provideDefaultCategoriesStringResource(
                            category
                        )
                    )
                } else {
                    category.note
                },
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}