package com.torrydo.transe.ui.mainAppScreen.settingScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.torrydo.transe.R
import com.torrydo.transe.databinding.FragmentSettingBinding
import com.torrydo.transe.ui.base.BaseFragment
import com.torrydo.transe.ui.mainAppScreen.MainActivity
import com.torrydo.transe.utils.CONSTANT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Named

@AndroidEntryPoint
@Named(CONSTANT.activityModule)
class SettingFragment : BaseFragment<SettingViewModel, FragmentSettingBinding>() {

    private val mViewModel: SettingViewModel by viewModels()

    override fun getViewModelClass() = mViewModel
    override fun getViewBinding() = FragmentSettingBinding.inflate(layoutInflater)

//    init {
//        VocabFragment.TAB_FINISHED = true
//    }

    @SuppressLint("ResourceType")
    override fun configOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        viewModel.userAccount.observe(viewLifecycleOwner,{
            binding.settingName.text = it.name
            binding.settingEmail.text = it.email
        })
        setup()
    }

    private fun setup() {

        // setup option notification
        binding.settingOption1.let { option1 ->
            option1.optionTitle.text = "Thông báo"
            option1.optionSwitch.setOnCheckedChangeListener { compoundButton, b ->
                if(b) viewModel.registerVocabNoti()
                else viewModel.unregisterVocabNoti()
            }
        }

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.theme_color)

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

