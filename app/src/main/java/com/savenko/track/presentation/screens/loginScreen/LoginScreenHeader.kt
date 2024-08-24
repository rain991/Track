package com.savenko.track.presentation.screens.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R

@Composable
fun LoginScreenHeader(modifier: Modifier) {
    val brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.primary,
        )
    )
    val appNameAnnotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                brush = brush, alpha = .8f, fontSize = 40.sp, shadow = Shadow(), fontWeight = FontWeight.W700
            )
        ) {
            append(stringResource(id = R.string.app_name))
        }
    }
    val appDescriptionAnnotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                brush = brush, alpha = .8f, fontSize = 13.sp, fontWeight = FontWeight.W500
            )
        ) {
            append(stringResource(id = R.string.logo_app_description))
        }
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.track_new_icon),
            contentDescription = stringResource(id = R.string.app_logo)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = appNameAnnotatedString,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = appDescriptionAnnotatedString,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
