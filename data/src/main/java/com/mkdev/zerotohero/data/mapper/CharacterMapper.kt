package com.mkdev.zerotohero.data.mapper

import com.mkdev.zerotohero.data.models.CharacterEntity
import com.mkdev.zerotohero.domain.models.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor(
    private val locationMapper: CharacterLocationMapper
) : Mapper<CharacterEntity, Character> {

    override fun mapFromEntity(type: CharacterEntity): Character {
        return Character(
            created = type.created,
            gender = type.gender,
            id = type.id,
            image = type.image,
            characterLocation = locationMapper.mapFromEntity(type.characterLocation),
            name = type.name,
            species = type.species,
            status = type.status,
            type = type.type,
            url = type.url,
            isBookMarked = type.isBookMarked
        )
    }

    override fun mapToEntity(type: Character): CharacterEntity {
        return CharacterEntity(
            created = type.created,
            gender = type.gender,
            id = type.id,
            image = type.image,
            characterLocation = locationMapper.mapToEntity(type.characterLocation),
            name = type.name,
            species = type.species,
            status = type.status,
            type = type.type,
            url = type.url,
            isBookMarked = type.isBookMarked
        )
    }
}
