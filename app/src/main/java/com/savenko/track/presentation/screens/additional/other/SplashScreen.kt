package com.savenko.track.presentation.screens.additional.other

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R

@Composable
fun SplashScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { }, bottomBar = { },
        floatingActionButton = { }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.onlyicon),
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier.size(160.dp)
            )
        }
    }
}