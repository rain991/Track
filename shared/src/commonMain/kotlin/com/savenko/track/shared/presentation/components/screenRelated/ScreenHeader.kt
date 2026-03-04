package com.savenko.track.shared.presentation.components.screenRelated

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import com.savenko.track.shared.Platform
import com.savenko.track.shared.PlatformTarget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Header(pageName: String) {
    val statusBarAwareModifier = if (Platform.type == PlatformTarget.Android) {
        Modifier.statusBarsPadding()
    } else {
        Modifier
    }
    Row(
        modifier = statusBarAwareModifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(Res.drawable.track_new_icon), contentDescription = null)
        Text(
            text = pageName,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Center
        )
    }
}
