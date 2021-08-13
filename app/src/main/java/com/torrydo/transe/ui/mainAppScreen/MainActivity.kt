package com.torrydo.transe.ui.mainAppScreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.torrydo.transe.R
import com.torrydo.transe.databinding.ActivityMainBinding
import com.torrydo.transe.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val vMainVM: MainViewModel by viewModels()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun getViewModelClass() = vMainVM

    override fun configOnCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()

        (supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment).also {
            binding.mainBottomNavView.setupWithNavController(it.navController)


        }

    }

}