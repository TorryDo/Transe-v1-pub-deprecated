package com.torrydo.transe.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<VM: ViewModel , B : ViewBinding> : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var binding: B

    abstract fun getViewBinding(): B
    abstract fun getViewModelClass(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        viewModel = getViewModelClass()

        configOnCreate(savedInstanceState)
    }

    abstract fun configOnCreate(savedInstanceState: Bundle?)


}