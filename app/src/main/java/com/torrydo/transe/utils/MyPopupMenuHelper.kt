package com.torrydo.transe.utils

import android.content.Context
import android.view.Menu
import android.view.View
import android.widget.PopupMenu

class MyPopupMenuHelper(
    val context: Context
) {

    fun show(
        view: View,
        stringList: List<String>,
        clickAtPosition: (clickAt: Int) -> Unit
    ) {
        var popupMenu: PopupMenu? = PopupMenu(view.context, view)
        var menu: Menu? = popupMenu!!.menu

        for (i in stringList.indices) {
            menu?.add(0, i, 0, stringList[i])
        }


        popupMenu.setOnDismissListener {
            popupMenu = null
            menu = null
        }

        popupMenu?.setOnMenuItemClickListener {
            clickAtPosition(it.itemId)

            popupMenu = null
            menu = null
            false

        }
        popupMenu?.show()
    }

}