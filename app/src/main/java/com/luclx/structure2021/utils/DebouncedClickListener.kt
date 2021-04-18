package com.luclx.structure2021.utils

import android.os.SystemClock
import android.view.View

class DebouncedClickListener @JvmOverloads constructor(
    private var defaultInterval: Long = 1000,
    private var listener: (() -> Unit)? = null
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        listener?.invoke()
    }
}
