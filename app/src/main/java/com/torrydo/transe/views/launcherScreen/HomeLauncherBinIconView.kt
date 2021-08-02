package com.torrydo.transe.views.launcherScreen

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.torrydo.transe.R
import com.torrydo.transe.databinding.ViewBinIconBinding
import com.torrydo.transe.views.base.HomeLauncherBaseView

class HomeLauncherBinIconView(
    val context: Context,
    windowManager: WindowManager
) : HomeLauncherBaseView<ViewBinIconBinding>(windowManager) {

    init {
        initViewFunction(ViewBinIconBinding.inflate(LayoutInflater.from(context)))
        initViewLayoutParams()
    }


    override fun initViewLayoutParams() {
        super.initViewLayoutParams()

        viewParams?.apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER
            flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            windowAnimations = R.style.IconStyle
        }


    }

}