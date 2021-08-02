package com.torrydo.transeng.utils

import android.content.Context
import android.view.Menu
import android.view.View
import android.widget.PopupMenu

class MyPopupMenuHelper(
    val context: Context,
    val deleteThis: () -> Unit  // change later
) {

    fun show(view: View) {
        var popupMenu: PopupMenu? = PopupMenu(view.context, view)
        var menu: Menu? = popupMenu!!.menu

        menu?.add(0, 1, 0, "delete")

        popupMenu.setOnDismissListener {
            popupMenu = null
            menu = null
        }

        popupMenu?.setOnMenuItemClickListener {
            when (it.itemId) {
                1 -> {
//                                onClickAt.onClickAt(view, vocabHolder.layoutPosition)
                    deleteThis()
                    false
                }
                else -> {
                    false
                }
            }
        }
        popupMenu?.show()
    }

}