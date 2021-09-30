package com.mkdev.zerotohero.domain.interactor

import com.mkdev.zerotohero.domain.models.Character
import com.mkdev.zerotohero.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseUseCase<Unit, Flow<List<Character>>> {
    override suspend fun invoke(params: Unit): Flow<List<Character>> =
        characterRepository.getCharacters()
}