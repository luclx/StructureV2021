package com.luclx.structure2021.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

fun RecyclerView.checkReachedLastItem(callback: Boolean.() -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val lm = (recyclerView.layoutManager as LinearLayoutManager)

            val visibleItemCount = lm.childCount
            val totalItemCount = lm.itemCount
            val firstVisibleItemPosition = lm.findFirstVisibleItemPosition()

            // if we have reach the end to the recyclerView
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                callback(true)
            } else {
                callback(false)
            }
        }
    })
}

fun View.setOnSafetyClickListener(time: Long = 1000, callBack: () -> Unit) {
    this.setOnClickListener(DebouncedClickListener(time) {
        callBack.invoke()
    })
}

fun EditText.showKeyBoard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInputFromWindow(
        this.applicationWindowToken,
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
    this.requestFocus()
}

fun EditText.hideKeyBoard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(applicationWindowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun String.toDate(): Date? {
    //2020-06-09T23:15:00.000Z
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.parse(this)
}

fun String.toAlertDate(): String {
    //2020-07-05 08:17:51
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    sdf.parse(this)?.let {
        val timeFormatString = "h:mm aa"
        val now = Calendar.getInstance()
        val smsTime = Calendar.getInstance()
        smsTime.time = it
        return when {
            now.timeInMillis - smsTime.timeInMillis < 60 * 1000 -> "Now"
            now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ->
                DateFormat.format(
                    timeFormatString,
                    smsTime
                ).toString()
            now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1 ->
                "Yesterday, " + DateFormat.format(
                    timeFormatString,
                    smsTime
                )
            else -> DateFormat.format("dd-MM-yyyy, h:mm aa", smsTime).toString()
        }
    } ?: kotlin.run {
        return this
    }
}

fun String.getTimeServer(): String {
    val smsTime = Calendar.getInstance()
    smsTime.timeInMillis = CommonUtils.parseDateTimeIso8601(this)
    val now = Calendar.getInstance()
    val timeFormatString = "h:mm aa"
    return when {
        now.timeInMillis - smsTime.timeInMillis < 60 * 1000 -> "Now"
        now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ->
            DateFormat.format(
                timeFormatString,
                smsTime
            ).toString()
        now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1 ->
            "Yesterday, " + DateFormat.format(
                timeFormatString,
                smsTime
            )
        else -> DateFormat.format("dd-MM-yyyy, h:mm aa", smsTime).toString()
    }
}

fun Context?.isInternetAvailable(): Boolean {
    this ?: return false
    var result = false
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}

fun EditText.showKeyboard(context: Context) {
    requestFocus()
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    setSelection(text.toString().length)
}

fun EditText.hideSoftKeyboard(context: Context) {
    clearFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

@Throws(IOException::class)
fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "BI_${timeStamp}_", /* prefix */
        ".png", /* suffix */
        storageDir /* directory */
    )
}