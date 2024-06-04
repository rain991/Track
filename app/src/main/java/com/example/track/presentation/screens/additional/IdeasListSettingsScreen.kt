package com.example.track.presentation.screens.additional

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.track.R
import com.example.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.example.track.presentation.components.mainScreen.dialogs.NewIdeaDialog
import com.example.track.presentation.components.screenComponents.additional.IdeasListSettingsScreenComponent
import com.example.track.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun IdeasListSettingsScreen(navController: NavHostController) {
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val isNewIdeaDialogVisible = newIdeaDialogViewModel.isNewIdeaDialogVisible.collectAsState()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { navController.navigate(Screen.MainScreen.route) }, modifier = Modifier.scale(0.8f)) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back to settings screen")
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.onlyicon), contentDescription = null, modifier = Modifier.size(36.dp))
                Text(
                    text = "Ideas", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ), color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1.2f))
            }
        },
        floatingActionButton = {
                ExtendedFloatingActionButton(
                    expanded = false,
                    onClick = { newIdeaDialogViewModel.setIsNewIdeaDialogVisible(true) },
                    icon = { Icon(Icons.Filled.Add, stringResource(R.string.add_new_idea)) },
                    text = { Text(text = stringResource(R.string.add_new_idea)) }, modifier = Modifier.padding(end = 16.dp, bottom = 16.dp))
        }
    ) {
        if (isNewIdeaDialogVisible.value) {
            NewIdeaDialog(newIdeaDialogViewModel)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            IdeasListSettingsScreenComponent()
        }
    }
}