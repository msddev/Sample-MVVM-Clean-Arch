package com.mkdev.zerotohero.remote

import com.mkdev.zerotohero.data.models.CharacterEntity
import com.mkdev.zerotohero.data.repository.CharacterRemote
import com.mkdev.zerotohero.remote.api.CharacterService
import com.mkdev.zerotohero.remote.mappers.CharacterEntityMapper
import javax.inject.Inject

class CharacterRemoteImpl @Inject constructor(
    private val characterService: CharacterService,
    private val characterEntityMapper: CharacterEntityMapper
) : CharacterRemote {
    override suspend fun getCharacters(): List<CharacterEntity> =
        characterService.getCharacters().characters.map {
            characterEntityMapper.mapFromModel(it)
        }

    override suspend fun getCharacter(characterId: Long): CharacterEntity =
        characterEntityMapper.mapFromModel(characterService.getCharacter(characterId))
}