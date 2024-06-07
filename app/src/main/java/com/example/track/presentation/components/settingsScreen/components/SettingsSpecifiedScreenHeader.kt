package com.example.track.presentation.components.settingsScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.track.R


@Composable
fun SettingsSpecifiedScreenHeader(screenName: String, onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { onBackPressed() }, modifier = Modifier.scale(0.8f)) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back to settings screen")
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.onlyicon), contentDescription = null, modifier = Modifier.size(36.dp))
        Text(
            text = screenName, style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            ), color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.weight(1.36f))
    }
}
