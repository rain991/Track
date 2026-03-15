package com.savenko.track.shared.domain.usecases.crud.userRelated

import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.presentation.themes.Themes
import com.savenko.track.shared.testdoubles.InMemoryPreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateUserDataUseCaseTest {
    private val dataStoreManager = DataStoreManager(InMemoryPreferencesDataStore())
    private val useCase = UpdateUserDataUseCase(dataStoreManager)

    @Test
    fun `new values are inserted correctly`() = runTest {
        useCase(key = DataStoreManager.NAME, value = "test user name")
        useCase(key = DataStoreManager.USE_SYSTEM_THEME, value = false)
        useCase(key = DataStoreManager.PREFERABLE_THEME, value = Themes.BlueTheme.name)
        useCase(key = DataStoreManager.BUDGET, value = 133.56f)
        useCase(key = DataStoreManager.SHOW_PAGE_NAME, value = true)

        assertEquals("test user name", dataStoreManager.nameFlow.first())
        assertEquals(false, dataStoreManager.useSystemTheme.first())
        assertEquals(Themes.BlueTheme.name, dataStoreManager.preferableTheme.first())
        assertEquals(133.56f, dataStoreManager.budgetFlow.first())
        assertEquals(true, dataStoreManager.isShowPageName.first())
    }
}
