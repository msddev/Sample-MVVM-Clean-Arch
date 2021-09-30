package com.mkdev.zerotohero.cache.mapper

import com.mkdev.zerotohero.cache.models.CharacterLocationCacheEntity
import com.mkdev.zerotohero.data.models.CharacterLocationEntity
import javax.inject.Inject

class CharacterLocationCacheMapper @Inject constructor() :
    CacheMapper<CharacterLocationCacheEntity, CharacterLocationEntity> {

    override fun mapFromCached(type: CharacterLocationCacheEntity): CharacterLocationEntity {
        return CharacterLocationEntity(name = type.name, url = type.url)
    }

    override fun mapToCached(type: CharacterLocationEntity): CharacterLocationCacheEntity {
        return CharacterLocationCacheEntity(name = type.name, url = type.url)
    }
}
