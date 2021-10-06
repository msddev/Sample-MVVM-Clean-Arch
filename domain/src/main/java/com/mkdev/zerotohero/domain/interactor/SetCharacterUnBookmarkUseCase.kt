package com.mkdev.zerotohero.domain.interactor

import com.mkdev.zerotohero.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetCharacterUnBookmarkUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseUseCase<Long, Flow<Int>> {
    override suspend operator fun invoke(params: Long) =
        characterRepository.setCharacterUnBookMarked(params)
}