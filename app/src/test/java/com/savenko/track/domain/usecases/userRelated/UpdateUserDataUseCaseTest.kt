package com.savenko.track.domain.usecases.userRelated

import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.presentation.themes.Themes
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset

class UpdateUserDataUseCaseTest() {
    private val dataStoreManager = mock<DataStoreManager>()
    private val updateUserDataUseCase = UpdateUserDataUseCase(dataStoreManager)

    companion object {
        @JvmStatic
        fun params() =
            listOf(
                Arguments.of(UpdateUserDataUseCaseParam.UserName()),
                Arguments.of(UpdateUserDataUseCaseParam.UseSystemTheme()),
                Arguments.of(UpdateUserDataUseCaseParam.PreferableTheme()),
                Arguments.of(UpdateUserDataUseCaseParam.Budget()),
                Arguments.of(UpdateUserDataUseCaseParam.ShowPageName())
            )
    }


    @AfterEach
    fun tearDown() {
        reset(dataStoreManager)
    }

    @ParameterizedTest
    @MethodSource("params")
    fun `new values are inserted correctly`(param: UpdateUserDataUseCaseParam) = runBlocking {
        val paramValue = param.param
        when (param) {
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