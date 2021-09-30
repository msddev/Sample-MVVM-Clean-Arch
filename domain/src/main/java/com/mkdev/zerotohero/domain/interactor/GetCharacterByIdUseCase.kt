package com.mkdev.zerotohero.domain.interactor

import com.mkdev.zerotohero.domain.models.Character
import com.mkdev.zerotohero.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseUseCase<Long, Flow<Character>> {
    override suspend fun invoke(params: Long): Flow<Character> =
        characterRepository.getCharacter(params)
}