package com.torrydo.transeng.views.launcherScreen

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.torrydo.transeng.R
import com.torrydo.transeng.databinding.ViewBinIconBinding
import com.torrydo.transeng.views.base.HomeLauncherBaseView

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