package com.mkdev.zerotohero.domain.interactor

import com.mkdev.zerotohero.domain.models.Settings
import com.mkdev.zerotohero.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseUseCase<Boolean, Flow<List<Settings>>> {
    override suspend fun invoke(params: Boolean): Flow<List<Settings>> =
        settingsRepository.getSettings(params)
}