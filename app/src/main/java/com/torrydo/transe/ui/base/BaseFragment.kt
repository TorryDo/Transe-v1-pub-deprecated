package com.torrydo.transe.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM : ViewModel, B : ViewBinding> : Fragment() {

    lateinit var viewModel: VM
    lateinit var binding: B

    abstract fun getViewModelClass(): VM
    abstract fun getViewBinding(): B

    abstract fun configOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = getViewModelClass()
        binding = getViewBinding()

        configOnCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

}