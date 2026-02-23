package com.savenko.track.shared.presentation.components.customComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    dashWidth: Dp = 8.dp,
    dashGap: Dp = 8.dp,
    thickness: Dp = 1.dp,
) {
    Row(
        modifier = modifier.then(Modifier.height(thickness))
    ) {
        val density = LocalDensity.current
        val windowInfo = LocalWindowInfo.current
        val screenWidth = with(density) { windowInfo.containerSize.width.toDp().value }
        val dashCount = screenWidth.div(dashWidth.value + dashGap.value).toInt()
        repeat(dashCount) {
            Box(
                modifier = Modifier
                    .width(dashWidth)
                    .fillMaxHeight()
                    .background(color)
            )
            Spacer(modifier = Modifier.width(dashGap))
        }
    }
}
@Composable
fun VerticalDashedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    dashHeight: Dp = 8.dp,
    dashGap: Dp = 8.dp,
    thickness: Dp = 1.dp,
) {
    Column(
        modifier = Modifier.width(thickness).then(modifier)
    ) {
        val density = LocalDensity.current
        val windowInfo = LocalWindowInfo.current
        val screenHeight = with(density) { windowInfo.containerSize.height.toDp().value }
        val dashCount = screenHeight.div(dashHeight.value + dashGap.value).toInt()
        repeat(dashCount) {
            Box(
                modifier = Modifier
                    .height(dashHeight)
                    .fillMaxWidth()
                    .background(color)
            )
            Spacer(modifier = Modifier.height(dashGap))
        }
    }
}
