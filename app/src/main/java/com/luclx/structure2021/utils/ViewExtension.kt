package com.luclx.structure2021.utils

import android.view.View

fun View.gone() {
    visibility = View.GONE
}

fun goneViews(vararg views: View) {
    views.forEach { it.gone() }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

fun View.visibleIf(condition: Boolean) {
    if (condition) visible() else gone()
}

fun View.goneIf(condition: Boolean) {
    if (condition) gone() else visible()
}
