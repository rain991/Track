package com.example.track.presentation.components.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.presentation.UiText.DatabaseStringResourcesProvider
import com.example.track.presentation.components.common.parser.parseColor

@Composable
fun CategoryChip(
    category: CategoryEntity,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    isSelected: Boolean,
    selectable: Boolean = true,
    onSelect: (CategoryEntity) -> Unit,
    chipScale: Float = 1.0f
) {
    val databaseStringResourcesProvider = DatabaseStringResourcesProvider()
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .scale(chipScale),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = parseColor(hexColor = category.colorId),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = parseColor(hexColor = category.colorId),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.checked_category_add_exp),
                    modifier = Modifier.fillMaxHeight(),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            if (isSelected) Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = databaseStringResourcesProvider.provideStringResource(category)),
                style = if (category.note.length < 12) {
                    textStyle
                } else {
                    MaterialTheme.typography.bodySmall
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(
                        if (selectable) {
                            0.84f
                        } else {
                            1.0f
                        }
                    ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Visible,
                maxLines = 1
            )
        }
    }
}