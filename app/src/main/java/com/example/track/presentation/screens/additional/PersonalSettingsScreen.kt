package com.example.track.presentation.screens.additional

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.track.R
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.presentation.navigation.Screen
import org.koin.compose.koinInject

@Composable
fun PersonalSettingsScreen(navController: NavHostController) {
    val settingsData = koinInject<DataStoreManager>()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier= Modifier.fillMaxWidth().padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically){
                Button(onClick = { navController.navigate(Screen.MainScreen.route) }, modifier= Modifier.scale(0.8f)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back to settings screen")
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.onlyicon), contentDescription = null, modifier = Modifier.size(36.dp))
                Text(text = "Personal Settings", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ), color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.weight(1.2f))
            }
        }
    )
    {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {

        }
    }
}