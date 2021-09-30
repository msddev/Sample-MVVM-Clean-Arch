package com.mkdev.zerotohero.remote.mappers

import com.mkdev.zerotohero.data.models.CharacterLocationEntity
import com.mkdev.zerotohero.remote.models.CharacterLocationModel
import javax.inject.Inject

class CharacterLocationEntityMapper @Inject constructor() :
    EntityMapper<CharacterLocationModel, CharacterLocationEntity> {
    override fun mapFromModel(model: CharacterLocationModel): CharacterLocationEntity {
        return CharacterLocationEntity(name = model.name, url = model.url)
    }
}
