package com.torrydo.transeng.ui.mainAppScreen.settingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.torrydo.transeng.databinding.FragmentSettingBinding
import com.torrydo.transeng.ui.base.BaseFragment
import com.torrydo.transeng.ui.mainAppScreen.vocabScreen.VocabViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Named

@AndroidEntryPoint
@Named("activityModule")
class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {

    private val mViewModel: SettingViewModel by viewModels()

    override fun getViewModelClass() = mViewModel
    override fun getViewBinding() = FragmentSettingBinding.inflate(layoutInflater)

    override fun configOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        viewModel
    }

}