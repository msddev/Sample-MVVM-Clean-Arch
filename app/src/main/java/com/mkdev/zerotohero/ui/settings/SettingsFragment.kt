package com.mkdev.zerotohero.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkdev.zerotohero.base.BaseFragment
import com.mkdev.zerotohero.core.theme.ThemeUtils
import com.mkdev.zerotohero.databinding.FragmentSettingsBinding
import com.mkdev.zerotohero.domain.models.SettingUiModel
import com.mkdev.zerotohero.extension.observe
import com.mkdev.zerotohero.presentation.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingAdapter: SettingsAdapter

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.settings, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getSettings()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = settingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        settingAdapter.setItemClickListener { selectedSetting ->
            viewModel.setSettings(selectedSetting)
        }
    }

    private fun onViewStateChange(result: SettingUiModel) {
        if (result.isRedelivered) return
        when (result) {
            is SettingUiModel.Loading -> {
                handleLoading(true)
            }
            is SettingUiModel.Success -> {
                handleLoading(false)
                settingAdapter.list = result.data
            }
            is SettingUiModel.Error -> {
                handleErrorMessage(result.error)
            }
            is SettingUiModel.NightMode -> {
                themeUtils.setNightMode(result.nightMode)
            }
        }
    }
}