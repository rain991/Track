package com.example.expensetracker.presentation

import android.content.res.Configuration
import android.media.Image
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.wear.compose.material.Text
import com.example.expensetracker.R
import com.example.expensetracker.presentation.themes.AppTheme
import com.example.expensetracker.ui.theme.focusedTextFieldText
import com.example.expensetracker.ui.theme.md_theme_light_primary
import com.example.expensetracker.ui.theme.unfocusedTextFieldText
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import java.time.LocalDate

@Composable
fun LoginScreen() {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LoginHeader()

            Spacer(modifier = Modifier.height(36.dp))

            Column(modifier = Modifier.padding(horizontal = 22.dp)) {
                LoginTextField(label = "Your First Name", trailing = "", modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = { TODO() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) md_theme_light_primary else Black,
                        contentColor = Color.White
                    ),
                    shape  = MaterialTheme.shapes.extraSmall
                ) {
                    Text(text = "Lets start!", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
                }


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
            contentScale = ContentScale.FillBounds
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
                        style = MaterialTheme.typography.titleSmall,
                        minLines = 2
                    )
                }
            }
        }
    }
}


@Composable
fun LoginTextField(modifier: Modifier = Modifier, label: String, trailing: String) {
    val uiColor = if (isSystemInDarkTheme()) Color.White else Black
    TextField(modifier = modifier, value = "", onValueChange = {},
        label = {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = uiColor)
        },
        colors = TextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedPlaceholderColor = MaterialTheme.colorScheme.focusedTextFieldText
        ),
        trailingIcon = {
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = trailing,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = uiColor
                )
            }
        }
    )
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Full Preview", showSystemUi = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        LoginScreen()
    }
}

@Composable
fun DateTimePicker(){
    val timePickerState = UseCaseState()

    Column(modifier = Modifier.fillMaxSize()) {


    }
    DateTimeDialog(state = timePickerState, selection = DateTimeSelection.Date{
            date-> Log.d("MyTag", "Selected date $date")
    }, properties = DialogProperties())
}

@Composable
fun DateTimeSample2(closeSelection: () -> Unit) {
    val timePickerState = rememberUseCaseState(visible = false, embedded = false)
    Button(onClick = { timePickerState.show() }) {
        Text(text = "Open DateTimePicker")
    }
//    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    DateTimeDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { closeSelection() }),
        selection = DateTimeSelection.DateTime{
            Log.d("MyLog", "Selected datetime is $it")
        }
    )
}

@Composable
fun Demo_DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

        }
    }
}