package com.savenko.track.presentation.components.customComponents

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.other.uiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.other.colors.parseColor
import org.koin.compose.koinInject

@Composable
fun CategorySettingsChip(
    category: CategoryEntity,
    isSelected: Boolean,
    borderColor: Color?,
    onSelect: (CategoryEntity) -> Unit
) {
    val databaseStringResourcesProvider = koinInject<DatabaseStringResourcesProvider>()
    val categoryColor = parseColor(hexColor = category.colorId)
    val categoryChipBorder: Float by animateFloatAsState(
        targetValue = if (isSelected) {
            2.0f
        } else {
            0.0f
        }, label = "categoriesSettingsChip"
    )
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .scale(0.9f),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = categoryColor,
            contentColor = Color.White,
            disabledContainerColor = categoryColor,
            disabledContentColor = Color.White
        ), border = if (borderColor != null) {
            BorderStroke((categoryChipBorder).dp, borderColor)
        } else {
            null
        }
    ) {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .wrapContentSize()
        ) {
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