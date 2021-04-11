package com.luclx.structure2021.base

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.luclx.structure2021.extension.setupErrorDialog
import com.luclx.structure2021.extension.setupLoadingDialog
import com.luclx.structure2021.extension.setupSnackBar

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupLoadingDialog(this, getViewModel().dialogLoading)
        setupErrorDialog(this, getViewModel().dialogError)
        setupSnackBar(this, getViewModel().snackBarError, Snackbar.LENGTH_LONG)
    }

    abstract fun getViewModel(): BaseViewModel
}