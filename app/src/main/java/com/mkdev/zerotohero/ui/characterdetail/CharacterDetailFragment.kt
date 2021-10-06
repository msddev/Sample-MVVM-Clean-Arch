package com.mkdev.zerotohero.ui.characterdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.mkdev.zerotohero.R
import com.mkdev.zerotohero.base.BaseFragment
import com.mkdev.zerotohero.databinding.FragmentCharacterDetailBinding
import com.mkdev.zerotohero.domain.models.Bookmark
import com.mkdev.zerotohero.domain.models.CharacterDetailUIModel
import com.mkdev.zerotohero.extension.observe
import com.mkdev.zerotohero.extension.showSnackBar
import com.mkdev.zerotohero.presentation.viewmodel.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>() {

    override val viewModel: CharacterDetailViewModel by viewModels()

    override fun getViewBinding(): FragmentCharacterDetailBinding =
        FragmentCharacterDetailBinding.inflate(layoutInflater)

    private val args: CharacterDetailFragmentArgs by navArgs()

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.characterDetail, ::onViewStateChange)
        viewModel.getCharacterDetail(args.characterId)
        setUiChangeListeners()
    }

    private fun setUiChangeListeners() {
        binding.checkBoxBookmark.setOnCheckedChangeListener { view, isChecked ->
            if (!binding.checkBoxBookmark.isPressed) {
                return@setOnCheckedChangeListener
            }
            if (isChecked)
                viewModel.setBookmarkCharacter(view.tag.toString().toLong())
            else
                viewModel.setUnBookmarkCharacter(view.tag.toString().toLong())
        }
    }

    private fun onViewStateChange(result: CharacterDetailUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is CharacterDetailUIModel.Loading -> {
                handleLoading(true)
            }
            is CharacterDetailUIModel.Success -> {
                handleLoading(false)
                result.data.let {
                    result.data.let { character ->
                        binding.apply {
                            textViewCharacterName.text = character.name
                            glide.load(character.image).into(imageViewCharacter)
                            checkBoxBookmark.tag = character.id
                            checkBoxBookmark.isChecked = character.isBookMarked
                            textViewSpecies.text = character.species
                            textViewGender.text = character.gender
                            textViewGenderLocation.text = character.characterLocation.name
                            textViewStatus.text = character.status
                        }
                    }
                }
            }
            is CharacterDetailUIModel.Error -> {
                handleErrorMessage(result.error)
            }
            is CharacterDetailUIModel.BookmarkStatus -> {
                when (result.bookmark) {
                    Bookmark.BOOKMARK ->
                        if (result.status) {
                            showSnackBar(binding.rootView, getString(R.string.bookmark_success))
                        } else {
                            handleErrorMessage(getString(R.string.bookmark_error))
                        }
                    Bookmark.UN_BOOKMARK ->
                        if (result.status) {
                            showSnackBar(
                                binding.rootView,
                                getString(R.string.un_bookmark_success)
                            )
                        } else {
                            handleErrorMessage(getString(R.string.bookmark_error))
                        }
                }
            }
        }
    }
}