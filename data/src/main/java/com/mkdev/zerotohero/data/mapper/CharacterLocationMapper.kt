package com.mkdev.zerotohero.data.mapper

import com.mkdev.zerotohero.data.models.CharacterLocationEntity
import com.mkdev.zerotohero.domain.models.CharacterLocation
import javax.inject.Inject

class CharacterLocationMapper @Inject constructor() :
    Mapper<CharacterLocationEntity, CharacterLocation> {

    override fun mapFromEntity(type: CharacterLocationEntity): CharacterLocation {
        return CharacterLocation(name = type.name, url = type.url)
    }

    override fun mapToEntity(type: CharacterLocation): CharacterLocationEntity {
        return CharacterLocationEntity(name = type.name, url = type.url)
    }
}
