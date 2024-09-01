package com.savenko.track.presentation.components.customComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.UiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.other.colors.parseColor
import org.koin.compose.koinInject

@Composable
fun CategoryChip(
    modifier: Modifier,
    category: CategoryEntity,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    isSelected: Boolean,
    onSelect: (CategoryEntity) -> Unit,
    borderColor: Color? = null,
    chipScale: Float = 1.0f
) {
    val databaseStringResourcesProvider = koinInject<DatabaseStringResourcesProvider>()
    val buttonColor = parseColor(hexColor = category.colorId)
    Button(
        modifier = modifier.then(Modifier.scale(chipScale)),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = buttonColor,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = buttonColor,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        border = if (borderColor != null) {
            BorderStroke((1).dp, borderColor)
        } else {
            null
        }
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.checked_category_add_exp_CD),
                    modifier = Modifier.fillMaxHeight(),
                    tint = Color.White
                )
            }
            if (isSelected) Spacer(modifier = Modifier.width(4.dp))
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
                style = textStyle,
                color = Color.White,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Visible,
                maxLines = 1
            )
        }
    }
}
