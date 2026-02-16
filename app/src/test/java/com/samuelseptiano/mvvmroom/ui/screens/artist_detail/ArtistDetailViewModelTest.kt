@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.app.viewmodel

import com.samuelseptiano.mvvmroom.data.model.AlertItem
import com.samuelseptiano.mvvmroom.data.model.roommodel.AlertItemRoomModel
import com.samuelseptiano.mvvmroom.data.repository.local.LocalAlertRepositoryImpl
import com.samuelseptiano.mvvmroom.data.repository.remote.NewsRepositoryImpl
import com.samuelseptiano.mvvmroom.ui.screens.headlineScreen.HeadlineViewModel
import com.samuelseptiano.mvvmroom.utils.network.DataState
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val alertRepository: NewsRepositoryImpl = mockk()
    private val localAlertRepository: LocalAlertRepositoryImpl = mockk()
    private lateinit var viewModel: HeadlineViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HeadlineViewModel(alertRepository, localAlertRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `loadAlertList emits success and updates uiState`() = runTest {
        val fakeAlerts = listOf(
            AlertItem(
                id = "1",
                threat_type = "Phishing",
                severity = "High",
                source = "Source1",
                timestamp = ""
            )
        )
        coEvery { alertRepository.getListStatus() } returns flow {
            emit(DataState.Loading)
            emit(DataState.Success(fakeAlerts))
        }
        coEvery { localAlertRepository.insertAlertBatch(any()) } just Runs

        viewModel.loadAlertList()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(fakeAlerts, state.alertList)
        assertNull(state.error)
        coVerify { localAlertRepository.insertAlertBatch(any()) }
    }

    @Test
    fun `loadAlertList emits error updates uiState`() = runTest {
        val exception = RuntimeException("Network error")

        coEvery { alertRepository.getListStatus() } returns flow {
            emit(DataState.Error(exception))
        }

        viewModel.loadAlertList()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(exception, state.error)
    }

    @Test
    fun `loadAlertListLocal loads from local repository`() = runTest {
        val fakeLocal = listOf(
            AlertItemRoomModel(
                id = "1",
                threatType = "Ransomware",
                severity = "High",
                source = "Source1",
                timestamp = ""
            )
        )

        // Mock repository
        coEvery { localAlertRepository.getLocalAlert() } returns fakeLocal

        mockkObject(AlertItemRoomModel.Companion)
        every { AlertItemRoomModel.Companion.toAlertItem(any()) } returns AlertItem(
            id = "1",
            threat_type = "Ransomware",
            severity = "High",
            source = "Source1",
            timestamp = ""
        )

        // Run
        viewModel.loadAlertListLocal()
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals("Ransomware", state.alertList?.first()?.threat_type)
        assertNull(state.error)

        // Clean up mock object
        unmockkObject(AlertItemRoomModel.Companion)
    }


}
