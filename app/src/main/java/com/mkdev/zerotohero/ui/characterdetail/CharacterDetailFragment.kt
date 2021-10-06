package com.mkdev.zerotohero.ui.characterdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mkdev.zerotohero.base.BaseFragment
import com.mkdev.zerotohero.databinding.FragmentCharacterDetailBinding
import com.mkdev.zerotohero.domain.models.CharacterDetailUIModel
import com.mkdev.zerotohero.extension.observe
import com.mkdev.zerotohero.presentation.viewmodel.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>() {

    override val viewModel: CharacterDetailViewModel by viewModels()

    override fun getViewBinding(): FragmentCharacterDetailBinding =
        FragmentCharacterDetailBinding.inflate(layoutInflater)

    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.characterDetail, ::onViewStateChange)
    }

    private fun onViewStateChange(result: CharacterDetailUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is CharacterDetailUIModel.Loading -> {
                handleLoading(true)
            }
            is CharacterDetailUIModel.Success -> {
                handleLoading(false)
            }
            is CharacterDetailUIModel.Error -> {
                handleErrorMessage(result.error)
            }
        }
    }
}