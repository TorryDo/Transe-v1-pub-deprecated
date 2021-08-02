package com.torrydo.transe.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Utils {

    companion object {

        private var imm: InputMethodManager? = null
        fun showKeyboard(context: Context, view: View) {

            view.requestFocus()

            if (imm == null) {
                imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        fun hideKeyboard(context: Context, view: View) {
            if (imm == null) {
                imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            }
            imm?.hideSoftInputFromWindow(view.rootView.windowToken, 0)
        }

        fun getDeviceScreenInfo(
            activity: Activity,
            isWidth: Boolean
        ): Int {
            var returnNum = 0

            val outMetrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val display = activity.display
                display?.getRealMetrics(outMetrics)

                returnNum = if (isWidth) {
                    outMetrics.widthPixels
                } else {
                    outMetrics.heightPixels
                }

            } else {
                @Suppress("DEPRECATION")
                val display = activity.windowManager.defaultDisplay
                @Suppress("DEPRECATION")
                display.getRealMetrics(outMetrics)

                returnNum = if (isWidth) {
                    outMetrics.widthPixels
                } else {
                    outMetrics.heightPixels
                }

            }
            return returnNum

        }

        @OptIn(DelicateCoroutinesApi::class)
        fun showShortToast(context: Context, message: String) {
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        fun showLongToast(context: Context, message: String) {
            GlobalScope.launch(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

}