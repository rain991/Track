package com.savenko.track.presentation.components.customComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.savenko.track.presentation.themes.purpleGreyTheme.tertiaryContainerDarkHighContrast
import com.savenko.track.presentation.themes.purpleGreyTheme.tertiaryContainerLightMediumContrast


@Composable
fun MainScreenFloatingActionButton(
    isButtonExpanded: Boolean,
    text: String = "",
    onClick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val gradientColor1 = MaterialTheme.colorScheme.primary
    val gradientColor2 = if(!isDarkTheme){
        tertiaryContainerLightMediumContrast
    }else{
        tertiaryContainerDarkHighContrast
    }
    val brush = remember {
        Brush.linearGradient(
            listOf(
                gradientColor1,
                gradientColor2
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, end = 16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = Modifier.background(brush, shape = ButtonDefaults.shape)
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = if (isButtonExpanded) {
                            8.dp
                        } else {
                            0.dp
                        }, vertical = 8.dp
                    ), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = text,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    if (isButtonExpanded) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = text, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}
