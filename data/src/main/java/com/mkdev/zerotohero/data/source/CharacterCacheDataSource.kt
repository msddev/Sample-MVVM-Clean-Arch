package com.mkdev.zerotohero.data.source

import com.mkdev.zerotohero.data.models.CharacterEntity
import com.mkdev.zerotohero.data.repository.CharacterCache
import com.mkdev.zerotohero.data.repository.CharacterDataSource
import javax.inject.Inject

class CharacterCacheDataSource @Inject constructor(
    private val characterCache: CharacterCache
) : CharacterDataSource {
    override suspend fun getCharacters(): List<CharacterEntity> =
        characterCache.getCharacters()

    override suspend fun getCharacter(characterId: Long): CharacterEntity =
        characterCache.getCharacter(characterId)

    override suspend fun saveCharacters(listCharacters: List<CharacterEntity>) =
        characterCache.saveCharacters(listCharacters)

    override suspend fun getBookMarkedCharacters(): List<CharacterEntity> =
        characterCache.getBookMarkedCharacters()

    override suspend fun setCharacterBookmarked(characterId: Long): Int =
        characterCache.setCharacterBookmarked(characterId)

    override suspend fun setCharacterUnBookMarked(characterId: Long): Int =
        characterCache.setCharacterUnBookMarked(characterId)

    override suspend fun isCached(): Boolean =
        characterCache.isCached()
}