package com.torrydo.transeng.views.base

import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import androidx.viewbinding.ViewBinding

open class HomeLauncherBaseView<B : ViewBinding>(
    private val windowManager: WindowManager
) {

    var viewParams: WindowManager.LayoutParams? = null

    lateinit var binding: B

    open fun initViewFunction(viewBinding: B) {
        this.binding = viewBinding
        this.viewParams = WindowManager.LayoutParams()
    }

    open fun addView() = windowManager.addView(binding.root, viewParams)
    open fun removeView() = windowManager.removeView(binding.root)
    open fun updateView() = windowManager.updateViewLayout(binding.root, viewParams)


    open fun initViewLayoutParams() {
        viewParams?.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT

            gravity = Gravity.CENTER
            format = PixelFormat.TRANSLUCENT
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                // use for android version lower than 8
                WindowManager.LayoutParams.TYPE_PHONE
            }
        }

    }

    fun getViewXY(): IntArray {
        val arr = IntArray(2)
        binding.root.getLocationOnScreen(arr)

        return arr
    }
}