package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import com.example.expensetracker.data.SettingsData
import com.example.expensetracker.presentation.AppTheme
import com.example.expensetracker.presentation.PagerTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var expensesDAO: ExpensesDAO

    private var firstLogin: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesDAO = ExpensesDB.getInstance(applicationContext).dao
        val dataStoreManager = DataStoreManager(this)
        CoroutineScope(Dispatchers.IO).launch {
            ExpensesListRepositoryImpl.setExpensesList(expensesDAO)
        }

//        CoroutineScope(Dispatchers.IO).launch {
//            firstLogin=dataStoreManager.getSettings().first().loginCount
//        }
//        if (firstLogin == 0) {
//            Log.d("MyLog", "FirstLogin , $firstLogin")
//            firstLogin++
//        }
//        if (firstLogin != 0) Log.d("MyLog", "SECOND LOGIN ${firstLogin}}")
//        CoroutineScope(Dispatchers.IO).launch {
//            // dataStoreManager.getSettings().collect{settings-> firstLogin=settings.loginCount}
//            dataStoreManager.saveSettings(SettingsData(firstLogin))
//        }



        //   Log.d("MyLog", "${ExpensesListRepositoryImpl.getExpensesList().size}")
        setContent {
            AppTheme {
                PagerTest(expensesDAO)

                val coroutine = rememberCoroutineScope()
                LaunchedEffect(key1 = true){
                    coroutine.launch {
                        firstLogin = dataStoreManager.getSettings().first().loginCount
                    }
                    if (firstLogin == 0) {
                        Log.d("MyLog", "FirstLogin , $firstLogin")
                        firstLogin++
                    }
                    if (firstLogin != 0) Log.d("MyLog", "SECOND LOGIN ${firstLogin}}")
                    CoroutineScope(Dispatchers.IO).launch {
                        dataStoreManager.saveSettings(SettingsData(firstLogin))
                    }

                }
                // val booleanValue by booleanFlow.collectAsState(initial = false) WILL BE USED FOR UI SETTINGS
            }




        }
    }
}
