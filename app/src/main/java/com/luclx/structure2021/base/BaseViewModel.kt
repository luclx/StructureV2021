package com.luclx.structure2021.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luclx.structure2021.event.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + SupervisorJob()

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    /**
     * FOR ERROR HANDLER AND LOADING HANDLER
     * We can triggers a snackBar message or dialog message when the value contained by errorTaskMessageLiveEvent is modified.
     */
    private val _dialogError = MutableLiveData<Event<String>>()
    val dialogError: LiveData<Event<String>> get() = _dialogError

    private val _dialogLoading = MutableLiveData<Event<Boolean>>()
    val dialogLoading: LiveData<Event<Boolean>> get() = _dialogLoading

    private val _snackBarError = MutableLiveData<Event<String>>()
    val snackBarError: LiveData<Event<String>> get() = _snackBarError

    /**
     * BaseEntity is REQUIRED
     */

    fun BaseError?.getErrorMessage(): String {
        return if (this?.message?.isNotBlank() == true)
            this.message
        else
            "Đã có lỗi xảy ra, vui lòng thử lại"
    }

    fun showErrorDialog(error: BaseError?) {
        error?.let {
            if (it.message.isBlank())
                _dialogError.value = Event("Đã có lỗi xảy ra, vui lòng thử lại")
            else
                _dialogError.value = Event(error.message)
        } ?: run {
            _dialogError.value = Event("Đã có lỗi xảy ra, vui lòng thử lại")
        }
        // TODO SHOW NETWORKING DIALOG
    }

    fun showErrorDialog(message: String) {
        _dialogError.value = Event(message)
    }

    fun showErrorToast(error: BaseError) {
        _snackBarError.value = Event(error.message)
    }

    fun showErrorToast(message: String) {
        _snackBarError.value = Event(message)
    }

    fun showLoading(show: Boolean) {
        _dialogLoading.value = Event(show)
    }
}
