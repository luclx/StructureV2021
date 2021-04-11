package com.luclx.structure2021.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.luclx.structure2021.R
import com.luclx.structure2021.databinding.DialogLoadingBinding

class LoadingDialog(
    context: Context,
    show: Boolean
) : Dialog(context) {

    private val binding: DialogLoadingBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_loading,
        null,
        false
    )

    init {
        try {
            setContentView(binding.root)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.run {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
        }
        instance = null
    }

    override fun show() {
        try {
            super.show()
        } catch (e: Exception) {
        }
    }

    override fun cancel() {
        super.cancel()
        instance = null
    }

    companion object {
        @Volatile
        private var instance: LoadingDialog? = null

        fun getInstanceAndShow(
            context: Context,
            show: Boolean
        ): LoadingDialog? {

            instance?.run {
                if (isShowing || !show) dismiss()
                instance = null
            }

            if (show) {
                synchronized(this) {
                    LoadingDialog(context, show).also { instance = it }
                }.show()
            }

            return instance
        }
    }
}
