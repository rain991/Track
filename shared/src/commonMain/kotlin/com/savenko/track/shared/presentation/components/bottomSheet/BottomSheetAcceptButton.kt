package com.savenko.track.shared.presentation.components.bottomSheet

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.presentation.themes.purpleGreyTheme.tertiaryContainerDarkHighContrast
import com.savenko.track.shared.presentation.themes.purpleGreyTheme.tertiaryContainerLightMediumContrast

@Composable
fun BottomSheetAcceptButton(modifier : Modifier, onClick: () -> Unit) {
    val gradientColor1 = MaterialTheme.colorScheme.primary
    val isDarkTheme = isSystemInDarkTheme()
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
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.background(brush, shape = ButtonDefaults.shape)
        ) {
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier
                    .widthIn(60.dp)
                    .wrapContentHeight()
                    .padding(bottom = 4.dp),
                shape = RoundedCornerShape(80),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(
                    text = stringResource(Res.string.add_it),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W500)
                )
            }
        }
    }
}
