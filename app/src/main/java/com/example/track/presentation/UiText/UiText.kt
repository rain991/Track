package com.example.taskmaster.presentation.UiText

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

class UiText(
    @StringRes val resId: Int,
    vararg val args: Any
) {

    @Composable
    fun asString(): String {
        return stringResource(resId, *args)
    }

    fun asString(context: Context): String {
        return context.getString(resId, *args)
    }
}