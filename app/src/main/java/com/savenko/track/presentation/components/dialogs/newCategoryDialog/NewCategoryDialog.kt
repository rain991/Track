package com.savenko.track.presentation.components.dialogs.newCategoryDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.savenko.track.R
import com.savenko.track.data.other.constants.CATEGORIES_NAME_MAX_LENGTH
import com.savenko.track.domain.models.abstractLayer.CategoriesTypes
import com.savenko.track.presentation.components.customComponents.GradientInputTextField
import com.savenko.track.presentation.other.colors.generateRandomColor
import com.savenko.track.presentation.other.composableTypes.errors.NewCategoryDialogErrors
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.themePreferences.CircleWithBorder
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCategoryDialog(
    error: NewCategoryDialogErrors? = null,
    onDismissRequest: () -> Unit,
    onAccept: (categoryName: String, categoryType: CategoriesTypes, rawCategoryColor: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var newCategoryName by remember { mutableStateOf("") }
    var newCategoryType by remember { mutableStateOf<CategoriesTypes>(CategoriesTypes.ExpenseCategory) }
    var newRawCategoryColor by remember { mutableStateOf(generateRandomColor()) }  // Raw means it is not processed to be saved to Room
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                Modifier
                    .wrapContentHeight()
                    .padding(vertical = 16.dp, horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(R.string.create_new_category),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(0.64f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        val options = listOf(CategoriesTypes.ExpenseCategory, CategoriesTypes.IncomeCategory)
                        Row {
                            SingleChoiceSegmentedButtonRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                options.forEachIndexed { index, categoryType ->
                                    SegmentedButton(
                                        modifier = Modifier.safeContentPadding(),
                                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                                        onClick = { newCategoryType = categoryType },
                                        selected = newCategoryType == categoryType
                                    ) {
                                        Text(
                                            text = stringResource(id = categoryType.nameStringRes),
                                            style = MaterialTheme.typography.labelMedium,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            GradientInputTextField(value = newCategoryName, label = stringResource(R.string.category_name_new_category_dialog)) {
                                if (it.length < CATEGORIES_NAME_MAX_LENGTH) newCategoryName = it
                            }
                        }
                        if (error is NewCategoryDialogErrors.CategoryAlreadyExist) {
                            Text(text = stringResource(error.error), style = MaterialTheme.typography.labelSmall)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .weight(0.36f)
                            .height(intrinsicSize = IntrinsicSize.Max),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircleWithBorder(
                            circleColor = colorFromHex(newRawCategoryColor),
                            isBorderEnabled = true,
                            borderColor = MaterialTheme.colorScheme.primary,
                            circleRadius = 72
                        ) {
                            newRawCategoryColor = generateRandomColor()
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp), horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        modifier = Modifier.scale(0.8f),
                        onClick = { onDismissRequest() }) {
                        Text(stringResource(id = R.string.decline))
                    }
                    FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
                        coroutineScope.launch {
                            onAccept(newCategoryName, newCategoryType, newRawCategoryColor)
                        }
                    }) {
                        Text(stringResource(id = R.string.add))
                    }
                }
            }
        }
    }
}

fun colorFromHex(hex: String): Color {
    val colorLong = hex.removePrefix("0x").toLong(16)
    return Color(colorLong)
}