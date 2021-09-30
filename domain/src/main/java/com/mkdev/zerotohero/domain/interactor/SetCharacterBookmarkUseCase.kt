package com.mkdev.zerotohero.domain.interactor

import com.mkdev.zerotohero.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterBookmarkUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseUseCase<Long, Flow<Int>> {

    override suspend operator fun invoke(params: Long) =
        characterRepository.setCharacterBookmarked(params)
}
