package com.luclx.structure2021.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.luclx.structure2021.R
import com.luclx.structure2021.databinding.DialogErrorBinding

class ErrorDialog(
    context: Context,
    private val title: String? = null,
    private val message: String,
    private val onClickOk: ((v: View) -> Unit)? = null
) : Dialog(context) {

    private val binding: DialogErrorBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.dialog_error,
        null,
        false
    )

    init {
        setContentView(binding.root)
        init()
    }

    private fun init() = binding.run {
        tvTitleResponse.visibility = title
            ?.also(tvTitleResponse::setText)
            ?.let { View.VISIBLE } ?: View.GONE

        tvMessageResponse.text = message

        btnOkResponse.setOnClickListener {
            onClickOk?.invoke(it)
            dismiss()
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
        private var instance: ErrorDialog? = null

        fun getInstanceAndShow(
            context: Context,
            message: String,
            title: String? = null,
            onClickOk: ((v: View) -> Unit)? = null
        ) {

            instance?.run {
                if (isShowing) dismiss()
                instance = null
            }

            synchronized(this) {
                ErrorDialog(context, title, message, onClickOk).also { instance = it }
            }.show()
        }
    }
}