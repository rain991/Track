package com.example.track.presentation.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.models.other.CategoryEntity
import com.example.track.presentation.common.parser.parseColor

@Composable
fun CategoryChip(category: CategoryEntity, isSelected: Boolean, onSelect: (CategoryEntity) -> Unit) {
    Button(
     //   modifier = Modifier.height(32.dp),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = parseColor(hexColor = category.colorId),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = parseColor(hexColor = category.colorId),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = stringResource(R.string.checked_category_add_exp),
                    modifier = Modifier.height(28.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            if (isSelected) Spacer(modifier = Modifier.width(8.dp))
            Text(text = category.note, style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary))
        }
    }
}