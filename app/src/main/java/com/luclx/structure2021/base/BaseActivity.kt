package com.luclx.structure2021.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.luclx.structure2021.extension.setupErrorDialog
import com.luclx.structure2021.extension.setupLoadingDialog
import com.luclx.structure2021.extension.setupSnackBar

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setupLoadingDialog(this, getViewModel().dialogLoading)
        setupErrorDialog(this, getViewModel().dialogError)
        setupSnackBar(this, getViewModel().snackBarError, Snackbar.LENGTH_LONG)
    }

    abstract fun getViewModel(): BaseViewModel
}
