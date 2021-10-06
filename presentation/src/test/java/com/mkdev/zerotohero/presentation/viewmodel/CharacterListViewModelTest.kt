package com.mkdev.zerotohero.presentation.viewmodel

import androidx.lifecycle.Observer
import com.mkdev.zerotohero.domain.interactor.GetBookMarkedCharacterListUseCase
import com.mkdev.zerotohero.domain.interactor.GetCharacterListUseCase
import com.mkdev.zerotohero.domain.models.CharacterUIModel
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
class CharacterListViewModelTest : PresentationBaseTest() {

    @Mock
    private lateinit var getCharacterListUseCase: GetCharacterListUseCase

    @Mock
    private lateinit var getBookMarkedCharacterListUseCase: GetBookMarkedCharacterListUseCase

    private lateinit var characterListViewModel: CharacterListViewModel

    @Mock
    private lateinit var observer: Observer<CharacterUIModel>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        characterListViewModel = CharacterListViewModel(
            dispatcher,
            getCharacterListUseCase,
            getBookMarkedCharacterListUseCase
        )

        characterListViewModel.characterList.observeForever(observer)
    }

    @Test
    fun `get character should return character list rom use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val isBookmarked = false
            val characters = FakePresentationData.getCharacters(10, true, isBookmarked)
            whenever(getCharacterListUseCase(Unit)).thenReturn(flowOf(characters))

            // Act (When)
            characterListViewModel.getCharacters(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Success(characters))
        }

    @Test
    fun `get character should return empty character list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val isBookmarked = false
            val characters = FakePresentationData.getCharacters(0)
            whenever(getCharacterListUseCase(Unit)).thenReturn(flowOf(characters))

            // Act (When)
            characterListViewModel.getCharacters(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Success(characters))
        }

    @Test
    fun `get character should return error from use-case`() =
        dispatcher.test.runBlockingTest {
            // Arrange (Given)
            val isBookmarked = false
            val errorMessage = "Internal server error"
            whenever(getCharacterListUseCase(Unit)).thenAnswer {
                throw IOException(errorMessage)
            }

            // Act (When)
            characterListViewModel.getCharacters(isBookmarked)

            // Assert (Then)
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Error(errorMessage))
        }

    @Test
    fun `get bookmark should return bookmark list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Given
            val isBookmarked = true
            val bookmarks =
                FakePresentationData.getCharacters(10, isRandomId = true, isBookmarked = true)
            whenever(getBookMarkedCharacterListUseCase(Unit)).thenReturn(flowOf(bookmarks))

            // When
            characterListViewModel.getCharacters(isBookmarked)

            // Then
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Success(bookmarks))
        }

    @Test
    fun `get bookmark should return empty bookmark list from use-case`() =
        dispatcher.test.runBlockingTest {
            // Given
            val isBookmarked = true
            val bookmarks = FakePresentationData.getCharacters(0)
            whenever(getBookMarkedCharacterListUseCase(Unit)).thenReturn(flowOf(bookmarks))

            // When
            characterListViewModel.getCharacters(isBookmarked)

            // Then
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Success(bookmarks))
        }

    @Test
    fun `get bookmark should return error from use-case`() =
        dispatcher.test.runBlockingTest {
            // Given
            val isBookmarked = true
            val errorMessage = "Internal server error"
            whenever(getBookMarkedCharacterListUseCase(Unit)).thenAnswer {
                throw IOException(errorMessage)
            }

            // When
            characterListViewModel.getCharacters(isBookmarked)

            // Then
            verify(observer).onChanged(CharacterUIModel.Loading)
            verify(observer).onChanged(CharacterUIModel.Error(errorMessage))
        }

    @After
    fun tearDown() {
        characterListViewModel.onCleared()
    }
}