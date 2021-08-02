package com.torrydo.transeng.ui.mainAppScreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.torrydo.transeng.R
import com.torrydo.transeng.databinding.ActivityMainBinding
import com.torrydo.transeng.ui.base.BaseActivity
import com.torrydo.transeng.ui.mainAppScreen.vocabScreen.VocabViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun configOnCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        (supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment).also {
            binding.mainBottomNavView.setupWithNavController(it.navController)
        }

    }


}