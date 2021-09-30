package com.mkdev.zerotohero.data

import com.mkdev.zerotohero.data.mapper.CharacterMapper
import com.mkdev.zerotohero.data.source.CharacterDataSourceFactory
import com.mkdev.zerotohero.domain.models.Character
import com.mkdev.zerotohero.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDataSourceFactory: CharacterDataSourceFactory,
    private val characterMapper: CharacterMapper
) : CharacterRepository {
    override suspend fun getCharacters(): Flow<List<Character>> = flow {
        val isCached = characterDataSourceFactory.getCacheDataSource().isCached()
        val characterList = characterDataSourceFactory.getDataStore(isCached).getCharacters()
            .map { characterEntity ->
                characterMapper.mapFromEntity(characterEntity)
            }
        saveCharacters(characterList)
        emit(characterList)
    }

    override suspend fun getCharacter(characterId: Long): Flow<Character> = flow {
        var character = characterDataSourceFactory.getCacheDataSource().getCharacter(characterId)
        if (character.image.isEmpty()) {
            character = characterDataSourceFactory.getRemoteDataSource().getCharacter(characterId)
        }

        emit(characterMapper.mapFromEntity(character))
    }

    override suspend fun saveCharacters(listCharacters: List<Character>) {
        val characterEntities = listCharacters.map { character ->
            characterMapper.mapToEntity(character)
        }
        characterDataSourceFactory.getCacheDataSource()
            .saveCharacters(characterEntities)
    }

    override suspend fun getBookMarkedCharacters(): Flow<List<Character>> = flow {
        val bookMarkedCharacters =
            characterDataSourceFactory.getCacheDataSource().getBookMarkedCharacters()
                .map { characterEntity ->
                    characterMapper.mapFromEntity(characterEntity)
                }

        emit(bookMarkedCharacters)
    }

    override suspend fun setCharacterBookmarked(characterId: Long): Flow<Int> = flow {
        emit(characterDataSourceFactory.getCacheDataSource().setCharacterBookmarked(characterId))
    }

    override suspend fun setCharacterUnBookMarked(characterId: Long): Flow<Int> = flow {
        emit(characterDataSourceFactory.getCacheDataSource().setCharacterUnBookMarked(characterId))
    }
}
