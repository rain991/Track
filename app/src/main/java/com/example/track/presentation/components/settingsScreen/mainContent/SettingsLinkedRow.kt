package com.example.track.presentation.components.settingsScreen.mainContent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text

@Composable
fun SettingsLinkedRow(text : String, onClick: ()->Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick },  horizontalArrangement = Arrangement.SpaceEvenly){
        Text(text = text)
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = text)
    }
}