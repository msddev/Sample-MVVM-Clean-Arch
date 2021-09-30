package com.mkdev.zerotohero.data.repository

import com.mkdev.zerotohero.data.models.CharacterEntity

interface CharacterDataSource {
    // Remote and cache
    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun getCharacter(characterId: Long): CharacterEntity

    // Cache
    suspend fun saveCharacters(listCharacters: List<CharacterEntity>)
    suspend fun getBookMarkedCharacters(): List<CharacterEntity>
    suspend fun setCharacterBookmarked(characterId: Long): Int
    suspend fun setCharacterUnBookMarked(characterId: Long): Int
    suspend fun isCached(): Boolean
}
