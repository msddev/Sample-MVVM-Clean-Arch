package com.mkdev.zerotohero.domain.repository

import com.mkdev.zerotohero.domain.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>>
}