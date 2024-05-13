package com.example.track.presentation.components.common.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.presentation.components.common.parser.parseColor

@Composable
fun CategoryChip(category: CategoryEntity, isSelected: Boolean, onSelect: (CategoryEntity) -> Unit, chipScale : Float = 1.0f) {
    Button(
        modifier = Modifier.height(40.dp).scale(chipScale),
        onClick = { onSelect(category) },
        colors = ButtonColors(
            containerColor = parseColor(hexColor = category.colorId),
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = parseColor(hexColor = category.colorId),
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        // Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        AnimatedVisibility(visible = isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(R.string.checked_category_add_exp),
                modifier = Modifier.fillMaxHeight(),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        if (isSelected) Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = category.note,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxHeight().fillMaxWidth(0.7f),
            color = MaterialTheme.colorScheme.onPrimary
        )
        //  }
    }
}