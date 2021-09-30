package com.mkdev.zerotohero.data.repository

import com.mkdev.zerotohero.data.models.CharacterEntity

interface CharacterRemote {
    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun getCharacter(characterId: Long): CharacterEntity
}
