package com.torrydo.transe.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    lateinit var binding: B

    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        configOnCreate(savedInstanceState)
    }

    abstract fun configOnCreate(savedInstanceState: Bundle?)

}