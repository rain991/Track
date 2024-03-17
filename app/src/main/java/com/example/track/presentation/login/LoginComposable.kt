package com.example.track.presentation.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.track.R
import com.example.track.data.models.currency.Currency
import com.example.track.data.viewmodels.login.LoginViewModel
import com.example.track.presentation.navigation.Screen
import com.example.track.ui.theme.focusedTextFieldText
import com.example.track.ui.theme.md_theme_light_primary
import com.example.track.ui.theme.unfocusedTextFieldText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val FIRSTNAME_INPUT_ID = 102
const val INCOME_INPUT_ID = 703
@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel = koinViewModel<LoginViewModel>()
    val coroutineScope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LoginHeader()

            Spacer(modifier = Modifier.height(32.dp))

            LoginContent(loginViewModel, navController, coroutineScope)
        }
    }
}

@Composable
private fun LoginContent(loginViewModel: LoginViewModel, navController: NavController, coroutineScope: CoroutineScope) {
    Column(modifier = Modifier.padding(horizontal = 22.dp)) {
        LoginTextField(
            label = stringResource(R.string.loginnametextfield),
            modifier = Modifier.fillMaxWidth(),
            INPUT_ID = FIRSTNAME_INPUT_ID,
            loginViewModel = loginViewModel
        )
        Spacer(modifier = Modifier.height(20.dp))
        LoginTextField(
            label = stringResource(R.string.your_income),
            modifier = Modifier.fillMaxWidth(),
            INPUT_ID = INCOME_INPUT_ID,
            loginViewModel = loginViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        val currencySelectedState = loginViewModel.currencyStateFlow.collectAsState()
        CurrencyDropDownMenu(
            currencyList = loginViewModel.currencyList,
            selectedOption = currencySelectedState.value,
            onSelect = {
                loginViewModel.setCurrencyStateFlow(it)
            })
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDownMenu(currencyList: List<Currency>, selectedOption: Currency, onSelect: (Currency) -> Unit) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    var isExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        }
    ) {

        TextField(
            value = selectedOption.ticker,
            readOnly = true,
            onValueChange = {},
            label = { Text(stringResource(R.string.currency), style = TextStyle(color = uiColor)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isExpanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
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
                    },
                    trailingIcon = {
                        Text(text = selectionOption.ticker, color = uiColor)
                    }
                )
            }
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
        Box(contentAlignment = Alignment.Center, modifier = Modifier.offset(5.dp, (-75).dp)) {
            Row(
                modifier = Modifier.padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(68.dp),
                    painter = painterResource(id = R.drawable.onlyicon),
                    contentDescription = stringResource(id = R.string.app_logo)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(id = R.string.app_name_optimal),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(id = R.string.logo_app_description),
                        style = MaterialTheme.typography.bodyMedium,
                        minLines = 2
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginTextField(
    modifier: Modifier = Modifier,
    label: String,
    INPUT_ID: Int,
    loginViewModel: LoginViewModel //guides in which way should LoginTextField work
) {
    var textValue by remember { mutableStateOf("") }
    var firstNameData by remember { mutableStateOf("") }
    var incomeData by remember { mutableStateOf("") }
    val maxCharacters = 26
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    TextField(
        modifier = modifier,
        value = textValue,
        onValueChange = {
            if (it.length <= maxCharacters) {
                textValue = it
                if (INPUT_ID == INCOME_INPUT_ID) {
                    incomeData = it
                    loginViewModel.setIncomeStateFlow(it.toInt())
                } else {
                    firstNameData = it
                    loginViewModel.setFirstNameStateFlow(it)
                }
            }
        },
        label = {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = uiColor)
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText
        ),
        singleLine = true,
        keyboardOptions = if (INPUT_ID == INCOME_INPUT_ID) {
            KeyboardOptions(keyboardType = KeyboardType.Number)
        } else {
            KeyboardOptions(keyboardType = KeyboardType.Text)
        }
    )
}

