package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.personalPreferences

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun FlippableCard(
    modifier: Modifier,
    isFlipped: Boolean,
    frontContent: @Composable () -> Unit,
    backContent: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (isFlipped) 180f else 0f, label = "card flip")
    val focusRequester = remember { FocusRequester() }
    Card(
        modifier = modifier.then(
            Modifier
                .clickable { onClick() }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .focusRequester(focusRequester)
                .focusTarget()),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            focusedElevation = 8.dp
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Arrow Icon",
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            )
            if (rotation <= 90f) {
                frontContent()
            } else {
                backContent()
            }
        }
    }
}