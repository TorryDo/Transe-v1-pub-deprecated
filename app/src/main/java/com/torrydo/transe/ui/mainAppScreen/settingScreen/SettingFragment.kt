package com.torrydo.transe.ui.mainAppScreen.settingScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.torrydo.transe.R
import com.torrydo.transe.databinding.FragmentSettingBinding
import com.torrydo.transe.ui.base.BaseFragment
import com.torrydo.transe.ui.mainAppScreen.MainActivity
import com.torrydo.transe.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Named

@AndroidEntryPoint
@Named("activityModule")
class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {

    private val mViewModel: SettingViewModel by viewModels()

    override fun getViewModelClass() = mViewModel
    override fun getViewBinding() = FragmentSettingBinding.inflate(layoutInflater)

    @SuppressLint("ResourceType")
    override fun configOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding.viewModel = viewModel
        setup()
    }

    private fun setup() {

        binding.settingCard.setOnClickListener {
            val mainActivity = (requireActivity() as MainActivity)
            mainActivity.findNavController(R.id.mainFragmentContainerView)
                .navigate(R.id.action_settingFragment_to_signInFragment)
        }

        viewModel.updateSignInState()
        viewModel.isSignedIn.observe(this, {
            viewModel.updateUserAccount()
        })
    }
}

