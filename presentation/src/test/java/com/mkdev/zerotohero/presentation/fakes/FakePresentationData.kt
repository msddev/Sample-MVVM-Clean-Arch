package com.mkdev.zerotohero.presentation.fakes

import com.mkdev.zerotohero.domain.models.SettingType
import com.mkdev.zerotohero.domain.models.Settings
import com.mkdev.zerotohero.presentation.fakes.FakeValueFactory.randomInt
import com.mkdev.zerotohero.presentation.fakes.FakeValueFactory.randomString

object FakePresentationData {

    fun getSettings(size: Int): List<Settings> {
        val settings = mutableListOf<Settings>()
        repeat(size) {
            settings.add(createSetting())
        }

        return settings
    }

    private fun createSetting(): Settings {
        return Settings(
            id = randomInt(),
            type = SettingType.SWITCH,
            settingLabel = randomString(),
            settingValue = randomString()
        )
    }
}