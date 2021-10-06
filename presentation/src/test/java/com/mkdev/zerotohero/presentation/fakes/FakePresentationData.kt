package com.mkdev.zerotohero.presentation.fakes

import com.mkdev.zerotohero.domain.models.Character
import com.mkdev.zerotohero.domain.models.CharacterLocation
import com.mkdev.zerotohero.domain.models.SettingType
import com.mkdev.zerotohero.domain.models.Settings
import com.mkdev.zerotohero.presentation.fakes.FakeValueFactory.randomBoolean
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

    fun getCharacters(
        size: Int,
        isRandomId: Boolean = true,
        isBookmarked: Boolean = false
    ): List<Character> {
        val characters = mutableListOf<Character>()
        repeat(size) {
            characters.add(createCharacter(isRandomId, isBookmarked))
        }
        return characters
    }

    private fun createCharacter(isRandomId: Boolean, isBookmarked: Boolean): Character {
        return Character(
            created = randomString(),
            gender = randomString(),
            id = if (isRandomId) randomInt() else 1,
            image = randomString(),
            characterLocation = CharacterLocation(
                name = randomString(),
                url = randomString()
            ),
            name = randomString(),
            species = randomString(),
            status = randomString(),
            randomString(),
            url = randomString(),
            isBookMarked = if (isBookmarked) true else randomBoolean()
        )
    }
}