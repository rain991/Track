package com.example.expensetracker.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun LoginScreen() {
    Column {

        Text(
            text = "Username:"
            //   modifier = Spacing(8.dp),
            //  style = (+MaterialTheme.typography()).h4
        )

        Text(
            text = "Password:"
//            modifier = Spacing(8.dp),
//            style = (+MaterialTheme.typography()).h4
        )
//        Surface(border = Border(Color.Gray, 1.dp), modifier = Spacing(8.dp)) {
//            Padding(padding = 8.dp) {
//                PasswordTextField(
//                    value = passwordState.value,
//                    onValueChange = { passwordState.value = it }
//                )
//            }
//        }

//        if (userNameState.value.isNotEmpty()
//            && passwordState.value.isNotEmpty()
//        )
        //   {
        Row(horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                }
            ) {
                Text(text = "Login")
            }

        }
        //     } else {
        Text(
            text = "Please enter both username and password!"
            //  modifier = Spacing(8.dp),
            //   style = (+MaterialTheme.typography()).h6
        )
        //  }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    AppTheme {
        LoginScreen()
    }
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