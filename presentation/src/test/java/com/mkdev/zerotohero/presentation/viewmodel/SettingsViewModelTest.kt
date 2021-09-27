package com.mkdev.zerotohero.presentation.viewmodel

import androidx.lifecycle.Observer
import com.mkdev.zerotohero.domain.interactor.GetSettingsUseCase
import com.mkdev.zerotohero.domain.models.SettingUiModel
import com.mkdev.zerotohero.presentation.fakes.FakePresentationData
import com.mkdev.zerotohero.presentation.utils.PresentationBaseTest
import com.mkdev.zerotohero.presentation.utils.PresentationPreferencesHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class SettingsViewModelTest : PresentationBaseTest() {

    @Mock
    lateinit var settingsUseCase: GetSettingsUseCase

    @Mock
    lateinit var preferencesHelper: PresentationPreferencesHelper

    @Mock
    private lateinit var observer: Observer<SettingUiModel>

    private lateinit var settingViewModel: SettingsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        settingViewModel = SettingsViewModel(dispatcher, settingsUseCase, preferencesHelper)
        settingViewModel.settings.observeForever(observer)
    }

    @Test
    fun `get settings with night mode on should return settings list from use-case`() =
        dispatcher.test.runBlockingTest() {
            // Arrange (Given)
            val isNightMode = true
            whenever(preferencesHelper.isNightMode).thenReturn(isNightMode)
            val settings = FakePresentationData.getSettings(7)
            whenever(settingsUseCase(isNightMode)).thenReturn(flowOf(settings))

            // Act (When)
            settingViewModel.getSettings()

            // Assert (Then)
            verify(observer).onChanged(SettingUiModel.Loading)
            verify(observer).onChanged(SettingUiModel.Success(data = settings))
        }

    @Test
    fun `get settings with night mode off should return settings list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val isNightMode = false
            whenever(preferencesHelper.isNightMode).thenReturn(isNightMode)
            val settings = FakePresentationData.getSettings(7)
            whenever(settingsUseCase(isNightMode)).thenReturn(flowOf(settings))

            // Act (When)
            settingViewModel.getSettings()

            // Assert (Then)
            verify(observer).onChanged(SettingUiModel.Loading)
            verify(observer).onChanged(SettingUiModel.Success(settings))
        }

    @Test
    fun `get settings with night mode off should return settings empty list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val isNightMode = false
            whenever(preferencesHelper.isNightMode).thenReturn(isNightMode)
            val settings = FakePresentationData.getSettings(0)
            whenever(settingsUseCase(isNightMode)).thenReturn(flowOf(settings))

            // Act (When)
            settingViewModel.getSettings()

            // Assert (Then)
            verify(observer).onChanged(SettingUiModel.Loading)
            verify(observer).onChanged(SettingUiModel.Success(settings))
        }

    @Test
    fun `get settings with night mode on should return error from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val isNightMode = true
            whenever(preferencesHelper.isNightMode).thenReturn(isNightMode)
            val errorMessage = "Server Error"
            whenever(settingsUseCase(isNightMode)) doAnswer {
                throw Exception(errorMessage)
            }

            // Act (When)
            settingViewModel.getSettings()

            // Assert (Then)
            verify(observer).onChanged(SettingUiModel.Loading)
            verify(observer).onChanged(SettingUiModel.Error(errorMessage))
        }

    @Test
    fun `set settings with night mode on should return night mode on from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val nightMode = true
            val setting = FakePresentationData.getSettings(1).first()
            setting.selectedValue = nightMode

            // Act (When)
            settingViewModel.setSettings(setting)

            // Assert (Then)
            verify(preferencesHelper).isNightMode = nightMode
            verify(observer).onChanged(SettingUiModel.NightMode(nightMode))
        }

    @Test
    fun `set settings with night mode off should return night mode off from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val nightMode = false
            val setting = FakePresentationData.getSettings(1).first()
            setting.selectedValue = nightMode

            // Act (When)
            settingViewModel.setSettings(setting)

            // Assert (Then)
            verify(preferencesHelper).isNightMode = nightMode
            verify(observer).onChanged(SettingUiModel.NightMode(nightMode))
        }

    @After
    fun tearDown() {
        settingViewModel.onCleared()
    }
}