package com.savenko.track.presentation.components.login


import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.savenko.track.R
import com.savenko.track.data.other.constants.MAX_BUDGET_VALUE
import com.savenko.track.data.other.constants.NAME_MAX_LENGTH
import com.savenko.track.data.viewmodels.login.LoginViewModel
import com.savenko.track.presentation.components.common.ui.CurrencyDropDownMenu
import com.savenko.track.presentation.navigation.Screen
import com.savenko.track.ui.theme.md_theme_light_primary
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel = koinViewModel<LoginViewModel>()
    Surface(modifier = Modifier.fillMaxSize()) {
        val useDarkTheme = isSystemInDarkTheme()
        val view = LocalView.current
        val color = MaterialTheme.colorScheme.primary.toArgb()
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = color
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkTheme
            }
        }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            LoginHeader()
            Spacer(modifier = Modifier.height(32.dp))
            LoginContent(loginViewModel, navController)
        }
    }
}

@Composable
private fun LoginContent(loginViewModel: LoginViewModel, navController: NavController) {
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val screenState = loginViewModel.loginScreenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.name), style = MaterialTheme.typography.titleSmall)
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .width(IntrinsicSize.Min)
                    .padding(start = 12.dp),
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                value = screenState.value.name,
                onValueChange = { newText ->
                    if (newText.length < NAME_MAX_LENGTH) {
                        loginViewModel.setFirstNameStateFlow(newText)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        controller?.hide()
                        focusManager.clearFocus()
                    }
                ),
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.aprox_month_income_login_screen, screenState.value.currency.ticker),
                style = MaterialTheme.typography.titleSmall
            )
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .width(IntrinsicSize.Min)
                    .padding(start = 12.dp),
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                value = screenState.value.budget.toString(),
                onValueChange = { newText ->
                    try {
                        val value = newText.toFloat()
                        if (value < MAX_BUDGET_VALUE) {
                            loginViewModel.setIncomeStateFlow(
                                value
                            )
                        }
                    } catch (e: NumberFormatException) {
                        screenState.value.budget
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        controller?.hide()
                        focusManager.clearFocus()
                    }
                ),
                maxLines = 1,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(0.5f)) {
            CurrencyDropDownMenu(
                currencyList = loginViewModel.currencyList,
                selectedOption = screenState.value.currency,
                onSelect = {
                    loginViewModel.setCurrencyStateFlow(it)
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .wrapContentWidth()
                .height(40.dp),
            onClick = {
                coroutineScope.launch {
                    loginViewModel.addToDataStore()
                }
                navController.navigate(Screen.MainScreen.route)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_primary,
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Text(
                text = stringResource(R.string.lets_start),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}


@Composable
private fun LoginHeader() {
    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.45f),
            painter = painterResource(id = R.drawable.header),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
        )
        Box(contentAlignment = Alignment.Center, modifier = Modifier.offset(0.dp, (-75).dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(id = R.drawable.onlyicon),
                    contentDescription = stringResource(id = R.string.app_logo)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .wrapContentWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = stringResource(id = R.string.logo_app_description),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}