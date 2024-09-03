package com.savenko.track.domain.usecases.crud.userRelated

import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.presentation.themes.Themes
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset


@RunWith(Parameterized::class)
class UpdateUserDataUseCaseTest(private val updateUserDataUseCaseParam: UpdateUserDataUseCaseParam) {
    companion object {
        private lateinit var updateUserDataUseCase: UpdateUserDataUseCase
        private val dataStoreManager = mock<DataStoreManager>()

        @Before
        fun setup() {
            updateUserDataUseCase = UpdateUserDataUseCase(dataStoreManager)
        }

        @After
        fun tearDown() {
            reset(dataStoreManager)
        }

        @JvmStatic
        @Parameterized.Parameters
        fun params() = listOf(
            arrayOf(UpdateUserDataUseCaseParam.UserName()),
            arrayOf(UpdateUserDataUseCaseParam.UseSystemTheme()),
            arrayOf(UpdateUserDataUseCaseParam.PreferableTheme()),
            arrayOf(UpdateUserDataUseCaseParam.Budget()),
            arrayOf(UpdateUserDataUseCaseParam.ShowPageName())
        )
    }


    @Test
    fun `new values are inserted correctly`() = runTest {
        when (val paramValue = updateUserDataUseCaseParam.param) {
            is UpdateUserDataUseCaseParam.UserName -> {
                val currentParamValue = paramValue as String
                Mockito.`when`(dataStoreManager.nameFlow)
                    .thenReturn(flow { emit(currentParamValue) })
                updateUserDataUseCase(key = DataStoreManager.NAME, value = currentParamValue)
                Mockito.verify(dataStoreManager).setPreference(key = DataStoreManager.NAME, value = currentParamValue)
            }

            is UpdateUserDataUseCaseParam.Budget -> {
                val currentParamValue = paramValue as Float
                Mockito.`when`(dataStoreManager.budgetFlow)
                    .thenReturn(flow { emit(currentParamValue) })
                updateUserDataUseCase(key = DataStoreManager.BUDGET, value = currentParamValue)
                Mockito.verify(dataStoreManager).setPreference(key = DataStoreManager.BUDGET, value = currentParamValue)
            }

            is UpdateUserDataUseCaseParam.PreferableTheme -> {
                val currentParamValue = paramValue as String
                Mockito.`when`(dataStoreManager.preferableTheme)
                    .thenReturn(flow { emit(currentParamValue) })
                updateUserDataUseCase(key = DataStoreManager.PREFERABLE_THEME, value = currentParamValue)
                Mockito.verify(dataStoreManager)
                    .setPreference(key = DataStoreManager.PREFERABLE_THEME, value = currentParamValue)
            }

            is UpdateUserDataUseCaseParam.ShowPageName -> {
                val currentParamValue = paramValue as Boolean
                Mockito.`when`(dataStoreManager.isShowPageName)
                    .thenReturn(flow { emit(currentParamValue) })
                updateUserDataUseCase(key = DataStoreManager.SHOW_PAGE_NAME, value = currentParamValue)
                Mockito.verify(dataStoreManager)
                    .setPreference(key = DataStoreManager.SHOW_PAGE_NAME, value = currentParamValue)
            }

            is UpdateUserDataUseCaseParam.UseSystemTheme -> {
                val currentParamValue = paramValue as Boolean
                Mockito.`when`(dataStoreManager.useSystemTheme)
                    .thenReturn(flow { emit(currentParamValue) })
                updateUserDataUseCase(key = DataStoreManager.USE_SYSTEM_THEME, value = currentParamValue)
                Mockito.verify(dataStoreManager)
                    .setPreference(key = DataStoreManager.USE_SYSTEM_THEME, value = currentParamValue)
            }
        }
    }

    sealed class UpdateUserDataUseCaseParam(val param: Any) {
        class UserName : UpdateUserDataUseCaseParam(param = "test user name")
        class Budget : UpdateUserDataUseCaseParam(param = 133.56f)
        class UseSystemTheme : UpdateUserDataUseCaseParam(param = false)
        class ShowPageName : UpdateUserDataUseCaseParam(param = true)
        class PreferableTheme : UpdateUserDataUseCaseParam(param = Themes.BlueTheme.name)
    }
}