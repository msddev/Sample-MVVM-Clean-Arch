package com.mkdev.zerotohero.presentation.viewmodel

import androidx.lifecycle.Observer
import com.mkdev.zerotohero.domain.interactor.GetCharacterByIdUseCase
import com.mkdev.zerotohero.domain.interactor.SetCharacterBookmarkUseCase
import com.mkdev.zerotohero.domain.interactor.SetCharacterUnBookmarkUseCase
import com.mkdev.zerotohero.domain.models.Bookmark
import com.mkdev.zerotohero.domain.models.CharacterDetailUIModel
import com.mkdev.zerotohero.presentation.fakes.FakePresentationData
import com.mkdev.zerotohero.presentation.utils.PresentationBaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest : PresentationBaseTest() {

    lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Mock
    lateinit var characterByIdUseCase: GetCharacterByIdUseCase

    @Mock
    lateinit var characterBookmarkUseCase: SetCharacterBookmarkUseCase

    @Mock
    lateinit var characterUnBookmarkUseCase: SetCharacterUnBookmarkUseCase

    @Mock
    private lateinit var observer: Observer<CharacterDetailUIModel>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        characterDetailViewModel = CharacterDetailViewModel(
            dispatcher,
            characterByIdUseCase,
            characterBookmarkUseCase,
            characterUnBookmarkUseCase
        )
        characterDetailViewModel.characterDetail.observeForever(observer)
    }

    @Test
    fun `get character detail with character-id should return character complete detail from use-case `() =
        dispatcher.test.runBlockingTest {
            // Given
            val characterId = 1L
            val character = FakePresentationData.getCharacters(1)[0]
            whenever(characterByIdUseCase(characterId)).thenReturn(flowOf(character))

            // When
            characterDetailViewModel.getCharacterDetail(characterId)

            // Then
            verify(observer).onChanged(CharacterDetailUIModel.Loading)
            verify(observer).onChanged(CharacterDetailUIModel.Success(character))
        }

    @Test
    fun `get character detail with character-id should return error from use-case `() =
        dispatcher.test.runBlockingTest {
            // Given
            val characterId = 1L
            val errorMessage = "Internal server error"
            whenever(characterByIdUseCase(characterId)).thenAnswer {
                throw IOException(errorMessage)
            }

            // When
            characterDetailViewModel.getCharacterDetail(characterId)

            // Then
            verify(observer).onChanged(CharacterDetailUIModel.Loading)
            verify(observer).onChanged(CharacterDetailUIModel.Error(errorMessage))
        }

    @Test
    fun `set bookmark character with character-id should return success status from use-case `() =
        dispatcher.test.runBlockingTest {
            // Given
            val characterId = 1L
            val status = 1
            whenever(characterBookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // When
            characterDetailViewModel.setBookmarkCharacter(characterId)

            // Then
            verify(observer).onChanged(
                CharacterDetailUIModel.BookmarkStatus(
                    Bookmark.BOOKMARK,
                    true
                )
            )
        }

    @Test
    fun `set bookmark character with character-id should return failed status from use-case `() =
        dispatcher.test.runBlockingTest {
            // Given
            val characterId = 1L
            val status = 0
            whenever(characterBookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // When
            characterDetailViewModel.setBookmarkCharacter(characterId)

            // Then
            verify(observer).onChanged(
                CharacterDetailUIModel.BookmarkStatus(
                    Bookmark.BOOKMARK,
                    false
                )
            )
        }

    @Test
    fun `set un-bookmark character with character-id should return success status from use-case `() =
        dispatcher.test.runBlockingTest {
            // Given
            val characterId = 1L
            val status = 1
            whenever(characterUnBookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // When
            characterDetailViewModel.setUnBookmarkCharacter(characterId)

            // Then
            verify(observer).onChanged(
                CharacterDetailUIModel.BookmarkStatus(
                    Bookmark.UN_BOOKMARK,
                    true
                )
            )
        }

    @Test
    fun `set un-bookmark character with character-id should return failed status from use-case `() =
        dispatcher.test.runBlockingTest {
            // Given
            val characterId = 1L
            val status = 0
            whenever(characterUnBookmarkUseCase(characterId)).thenReturn(flowOf(status))

            // When
            characterDetailViewModel.setUnBookmarkCharacter(characterId)

            // Then
            verify(observer).onChanged(
                CharacterDetailUIModel.BookmarkStatus(
                    Bookmark.UN_BOOKMARK,
                    false
                )
            )
        }

    @After
    fun tearDown() {
        characterDetailViewModel.onCleared()
    }
}