package com.luclx.structure2021.extension

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.luclx.structure2021.dialog.ErrorDialog
import com.luclx.structure2021.dialog.LoadingDialog
import com.luclx.structure2021.event.Event

// FOR LOADING DIALOG HANDLER  EXTENSION

fun Fragment.showLoadingDialog(show: Boolean) {
    context?.let {
        LoadingDialog.getInstanceAndShow(it, show)
    }
}

fun Fragment.setupLoadingDialog(
    lifecycleOwner: LifecycleOwner,
    dialogEvent: LiveData<Event<Boolean>>
) {
    dialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { show ->
            context?.let { showLoadingDialog(show) }
        }
    })
}

// -- FOR ERROR DIALOG HANDLER EXTENSION

fun Fragment.showErrorDialog(message: String) {
    context?.let { ErrorDialog.getInstanceAndShow(it, message) }
}

fun Fragment.setupErrorDialog(
    lifecycleOwner: LifecycleOwner,
    dialogEvent: LiveData<Event<String>>
) {
    dialogEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { message ->
            context?.let { showErrorDialog(message) }
        }
    })
}

// FOR ATTENTION SNACK-BAR HANDLER EXTENSION

fun Fragment.showSnackBar(txt: String, timeLength: Int) {
    activity?.let {
        Snackbar.make(
            it.findViewById(android.R.id.content),
            txt,
            timeLength
        ).show()
    }
}

fun Fragment.setupSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackBarEvent: LiveData<Event<String>>,
    timeLength: Int
) {
    snackBarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { message ->
            context?.let { showSnackBar(message, timeLength) }
        }
    })
}

fun <T> Fragment?.openActivity(clazz: Class<T>) {
    this?.startActivity(Intent(context, clazz))
}

fun <T> Fragment?.openActivityForResult(clazz: Class<T>, resultCode: Int, bundle: Bundle? = null) {
    this ?: return
    val intent = Intent(context, clazz)
    bundle?.let {
        intent.putExtras(bundle)
    }
    startActivityForResult(intent, resultCode)
}

fun Fragment.replaceFragmentByTag(layoutId: Int, fragment: Fragment, tag: String) {
    try {
        childFragmentManager.beginTransaction()
            .replace(
                layoutId,
                fragment,
                tag
            )
            .addToBackStack(null)
            .commitAllowingStateLoss()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.removeFragmentByTag(tag: String) {
    try {
        childFragmentManager.findFragmentByTag(tag)?.let {
            Log.e("HomeFragment-TAG", "remove fragment $tag")
            childFragmentManager.beginTransaction()
                .remove(it)
                .commitAllowingStateLoss()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}