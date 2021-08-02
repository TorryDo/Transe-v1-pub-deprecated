package com.torrydo.transeng.utils

import android.content.res.Resources

object Converter {

    fun toPx(dp: Int): Int {
        return try {
            (dp * Resources.getSystem().displayMetrics.density).toInt()
        } catch (e: Exception) {
            0
        }
    }

    fun toDp(px: Int): Int {
        return try {
            (px / Resources.getSystem().displayMetrics.density).toInt()
        } catch (e: Exception) {
            0
        }
    }

}