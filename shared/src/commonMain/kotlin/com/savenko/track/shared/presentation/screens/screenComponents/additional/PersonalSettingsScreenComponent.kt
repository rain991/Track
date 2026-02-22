package com.savenko.track.shared.presentation.screens.screenComponents.additional

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.AppInfo
import com.savenko.track.shared.data.viewmodels.settingsScreen.personal.PersonalSettingsScreenViewmodel
import com.savenko.track.shared.data.viewmodels.settingsScreen.personal.PersonalStatsViewModel
import com.savenko.track.shared.presentation.screens.screenComponents.settingsScreenRelated.personalPreferences.PersonalSettingsContent
import com.savenko.track.shared.presentation.screens.screenComponents.settingsScreenRelated.personalPreferences.PersonalSettingsStatistics
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PersonalSettingsScreenComponent() {
    val personalSettingsScreenViewmodel = koinViewModel<PersonalSettingsScreenViewmodel>()
    val personalStatsViewmodel = koinViewModel<PersonalStatsViewModel>()
    val trackVersion = AppInfo.versionName
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(8.dp))
        PersonalSettingsContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), personalSettingsViewModel = personalSettingsScreenViewmodel
        )
        Spacer(Modifier.height(16.dp))
        PersonalSettingsStatistics(personalStatsViewmodel)
        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(Res.drawable.track_new_icon),
                contentDescription = null,
                modifier = Modifier.height(16.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = stringResource(Res.string.track_version, trackVersion),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}
