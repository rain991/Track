package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.viewmodels.UserDataViewModel
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.presentation.themes.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
class ExpenseTrackerActivity : ComponentActivity() {
    private val userDataViewModel: UserDataViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore : DataStoreManager by inject()
        CoroutineScope(Dispatchers.IO).launch {
            val actualLoginCount = dataStore.loginCountFlow.first()
            if(actualLoginCount>0) dataStore.incrementLoginCount()
        }
        setContent {
            AppTheme {
                Navigation(dataStore)
            }
        }
        Log.d("MyLog", "${userDataViewModel.currentUser}")
    }
}



