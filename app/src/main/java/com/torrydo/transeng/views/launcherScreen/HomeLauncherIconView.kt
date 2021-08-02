package com.torrydo.transeng.views.launcherScreen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import com.torrydo.transeng.R
import com.torrydo.transeng.databinding.ViewIconBinding
import com.torrydo.transeng.service.searchService.LauncherSearchService
import com.torrydo.transeng.utils.anim.AnimState
import com.torrydo.transeng.utils.anim.MyAnimationHelper
import com.torrydo.transeng.views.base.HomeLauncherBaseView

class HomeLauncherIconView(
    private val context: Context,
    private val windowManager: WindowManager

) : HomeLauncherBaseView<ViewIconBinding>(windowManager) {

    private val TAG = "_TAG_HomeLauncherIcon"

    private val myAnimationHelper = MyAnimationHelper()

    var mIconPrevX = 0
    var mIconPrevY = 0

    var mIconStartX = 0f
    var mIconStartY = 0f

    var newX = 0
    var newY = 0


    init {
        initViewFunction(ViewIconBinding.inflate(LayoutInflater.from(context)))
        initViewLayoutParams()
    }


    override fun initViewLayoutParams() {
        super.initViewLayoutParams()

        viewParams?.apply {
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            windowAnimations = R.style.IconStyle
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    fun customTouch(
        deviceHalfWidth: Int,
        deviceHalfHeight: Int,
        iconViewListener: IconViewListener
    ) {

        binding.homeLauncherMainIcon.let { icon ->

            icon.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {

                        if (LauncherSearchService.STATE == LAUNCHER_STATE.ICON) {
                            mIconPrevX = viewParams!!.x
                            mIconPrevY = viewParams!!.y
                        }

                        mIconStartX = motionEvent.rawX
                        mIconStartY = motionEvent.rawY

                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_MOVE -> {

                        val mIconDeltaX = motionEvent.rawX - mIconStartX
                        val mIconDeltaY = motionEvent.rawY - mIconStartY

                        if (LauncherSearchService.STATE == LAUNCHER_STATE.ICON) {
                            iconViewListener.showBin()
                        }

                        if (LauncherSearchService.STATE != LAUNCHER_STATE.BIN) {
                            LauncherSearchService.STATE = LAUNCHER_STATE.BIN
                        }

                        newX = mIconPrevX + mIconDeltaX.toInt()  // -540 .. 540
                        newY = mIconPrevY + mIconDeltaY.toInt()  // -1xxx .. 1xxx

                        viewParams!!.x = newX
                        viewParams!!.y = newY
                        updateView()

                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_UP -> {

                        // k cho tọa độ Y của view ra ngoài màn hình khiến user khó vuốt
                        if (newY > deviceHalfHeight - 150) {
                            newY = deviceHalfHeight - 150
                        } else if (newY < -deviceHalfHeight + 100) {
                            newY = -deviceHalfHeight + 100
                        }
                        viewParams!!.y = newY
                        updateView()

                        iconViewListener.actionUp()

                        if (LauncherSearchService.STATE == LAUNCHER_STATE.ICON) {
                            iconViewListener.showTrans()
                        }

                        if (LauncherSearchService.STATE == LAUNCHER_STATE.BIN) {
                            iconViewListener.removeBin()
                            LauncherSearchService.STATE = LAUNCHER_STATE.ICON
                        }

                        return@setOnTouchListener true
                    }

                    else -> return@setOnTouchListener false
                }
            }
        }
    }

    private var isAnimating = false
    fun animateIconToEdge(
        currentIconX: Int,    //    0..1080
        offsetPx: Int,        //    68
        deviceHalfWidth: Int,  //    540
        onFinished: () -> Unit
    ) {
        if (!isAnimating) {
            isAnimating = true

            if (currentIconX < deviceHalfWidth - offsetPx) {    // animate icon to the LEFT side

                val realX = deviceHalfWidth - currentIconX  // 235
                val leftEdgeX = deviceHalfWidth - offsetPx  // 540 - 68 = 472

                myAnimationHelper.startSpringX(
                    realX.toFloat(),
                    leftEdgeX.toFloat(),
                    object : AnimState {
                        override fun onUpdate(float: Float) {
                            try {
                                viewParams!!.x = -(float.toInt())
                                windowManager.updateViewLayout(binding.root, viewParams)
                            } catch (e: Exception) {
                            }

                        }

                        override fun onFinish() {
                            isAnimating = false
                            onFinished()
                        }
                    }
                )

            } else {                                            // animate icon to the RIGHT side

                val realX = currentIconX - deviceHalfWidth + offsetPx  // 235
                val rightEdgeX = deviceHalfWidth - offsetPx            // 540 - 68 = 472

                myAnimationHelper.startSpringX(
                    realX.toFloat(),
                    rightEdgeX.toFloat(),
                    object : AnimState {
                        override fun onUpdate(float: Float) {
                            try {
                                viewParams!!.x = float.toInt()
                                windowManager.updateViewLayout(binding.root, viewParams)
                            } catch (e: Exception) {
                            }

                        }

                        override fun onFinish() {
                            isAnimating = false
                            onFinished()
                        }
                    }
                )
            }
        }
    }
}