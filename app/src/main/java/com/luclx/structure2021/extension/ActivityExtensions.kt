package com.luclx.structure2021.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.luclx.structure2021.dialog.ErrorDialog
import com.luclx.structure2021.dialog.LoadingDialog
import com.luclx.structure2021.event.Event

fun AppCompatActivity.exit() = apply {
    Intent(Intent.ACTION_MAIN)
        .apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.run(::startActivity)
}

fun <T> Activity?.openActivity(clazz: Class<T>, bundle: Bundle? = null) {
    this?.startActivity(
        Intent(this, clazz)
            .apply {
                bundle?.let {
                    putExtras(bundle)
                }
            }
    )
}

fun <T> Activity?.openActivityForResult(clazz: Class<T>, resultCode: Int, bundle: Bundle? = null) {
    this ?: return
    val intent = Intent(this, clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent, resultCode)
}

fun Activity?.getRunningOrNull(): Activity? = this?.takeUnless { it.isDestroyed && it.isFinishing }

// FOR LOADING DIALOG HANDLER  EXTENSION

fun Activity.showLoadingDialog(show: Boolean) {
    LoadingDialog.getInstanceAndShow(this, show)
}

fun Activity.setupLoadingDialog(
    lifecycleOwner: LifecycleOwner,
    dialogEvent: LiveData<Event<Boolean>>
) {
    dialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { show ->
            showLoadingDialog(show)
        }
    })
}

// -- FOR ERROR DIALOG HANDLER EXTENSION

fun Activity.showErrorDialog(message: String) {
    ErrorDialog.getInstanceAndShow(this, message)
}

fun Activity.setupErrorDialog(
    lifecycleOwner: LifecycleOwner,
    dialogEvent: LiveData<Event<String>>
) {
    dialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { message ->
            showErrorDialog(message)
        }
    })
}

// FOR ATTENTION SNACKBAR HANDLER EXTENSION

fun Activity.showSnackBar(snackbarText: String, timeLength: Int) {
    Snackbar.make(
        this.findViewById<View>(android.R.id.content),
        snackbarText,
        timeLength
    ).show()
}

fun Activity.setupSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<String>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { message ->
            showSnackBar(message, timeLength)
        }
    })
}
