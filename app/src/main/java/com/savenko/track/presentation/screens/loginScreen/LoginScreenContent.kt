package com.savenko.track.presentation.screens.loginScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.savenko.track.R
import com.savenko.track.data.other.constants.MAX_BUDGET_VALUE
import com.savenko.track.data.other.constants.NAME_MAX_LENGTH
import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.data.viewmodels.login.LoginScreenViewModel
import com.savenko.track.data.viewmodels.settingsScreen.themePreferences.ThemePreferenceSettingsViewModel
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.components.customComponents.AutoResizedText
import com.savenko.track.presentation.components.customComponents.ThemeSelectorRow
import com.savenko.track.presentation.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreenContent(loginScreenViewModel: LoginScreenViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val themePreferenceViewModel = koinViewModel<ThemePreferenceSettingsViewModel>()
    val preferableTheme =
        themePreferenceViewModel.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current
    val nameFocusRequester = remember { FocusRequester() }
    val budgetFocusRequester = remember { FocusRequester() }
    val screenState = loginScreenViewModel.loginScreenState.collectAsState()
    val brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.primary,
        )
    )
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp)
            .background(brush = brush, shape = RoundedCornerShape(20)),
        shape = RoundedCornerShape(20),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.name),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(8.dp))
                BasicTextField(
                    modifier = Modifier
                        .focusRequester(nameFocusRequester)
                        .width(IntrinsicSize.Min)
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically),
                    textStyle = MaterialTheme.typography.displaySmall.copy(
                        letterSpacing = 1.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.W500
                    ),
                    value = screenState.value.name,
                    onValueChange = { newText ->
                        if (newText.length < NAME_MAX_LENGTH) {
                            loginScreenViewModel.setFirstNameStateFlow(newText)
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            controller?.hide()
                            focusManager.clearFocus()
                        }
                    )
                )
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.aprox_month_income_login_screen
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Row(modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        modifier = Modifier
                            .focusRequester(budgetFocusRequester)
                            .width(IntrinsicSize.Min)
                            .padding(start = 12.dp),
                        textStyle = MaterialTheme.typography.displaySmall.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.W500,
                            letterSpacing = (0.8).sp
                        ),
                        value = screenState.value.budget.toString(),
                        onValueChange = { newText ->
                            try {
                                val value = newText.toFloat()
                                if (value < MAX_BUDGET_VALUE) {
                                    loginScreenViewModel.setIncomeStateFlow(
                                        value
                                    )
                                }
                            } catch (e: NumberFormatException) {
                                screenState.value.budget
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                controller?.hide()
                                focusManager.clearFocus()
                            }
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    LoginScreenCurrencyPicker(
                        currencyList = loginScreenViewModel.currencyList,
                        selectedOption = screenState.value.currency,
                        onSelect = {
                            loginScreenViewModel.setCurrencyStateFlow(it)
                        }
                    )
                }
            }
            ThemeSelectorRow(preferableTheme = preferableTheme) { newTheme ->
                coroutineScope.launch {
                    themePreferenceViewModel.setPreferableTheme(newTheme)
                }
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        loginScreenViewModel.addToDataStore()
                    }
                    navController.navigate(Screen.MainScreen.route)
                },
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke((1).dp, MaterialTheme.colorScheme.onPrimary)
            ) {
                AutoResizedText(
                    text = stringResource(R.string.lets_start),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun LoginScreenCurrencyPicker(
    currencyList: List<Currency>,
    selectedOption: Currency,
    crossinline onSelect: (Currency) -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    var isExpanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
            if (isExpanded) {
                focusRequester.requestFocus()
            } else {
                focusManager.clearFocus()
            }
        }, modifier = Modifier
            .wrapContentWidth()
            .focusRequester(focusRequester)
    ) {
        Card(
            modifier = Modifier
                .menuAnchor()
                .scale(0.9f),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke((1).dp, MaterialTheme.colorScheme.primary)
        ) {
            BasicTextField(
                value = selectedOption.ticker,
                readOnly = true,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(8.dp),
                onValueChange = {},
                textStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary)
            )
        }
        ExposedDropdownMenu(
            modifier = Modifier.width(IntrinsicSize.Min),
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            currencyList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption.name, color = uiColor) },
                    onClick = {
                        onSelect(selectionOption)
                        isExpanded = false
                        focusManager.clearFocus()
                    },
                    trailingIcon = {
                        Text(text = selectionOption.ticker, color = uiColor)
                    }
                )
            }
        }
    }
}