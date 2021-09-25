package com.mkdev.zerotohero.domain.models

sealed class SettingUiModel {
    object Loading : SettingUiModel()
    data class Error(var error: String = "") : SettingUiModel()
    data class Success(var data: List<Settings> = emptyList()) : SettingUiModel()
    data class NightMode(val nightMode: Boolean) : SettingUiModel()
}
