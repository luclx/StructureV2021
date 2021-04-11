package com.luclx.structure2021.data

import com.luclx.structure2021.base.BaseViewModel

fun <T> handleResource(
    viewModel: BaseViewModel,
    resource: Resource<T>,
    onSuccessCallback: (data: T) -> Unit,
    onFailCallback: ((error: Resource.Error<T>) -> Unit)? = null
) {

    when (resource) {
        is Resource.Loading -> viewModel.showLoading(true)
        is Resource.Success -> {
            viewModel.showLoading(false)
            onSuccessCallback.invoke(resource.data)
        }
        is Resource.Error -> {
            viewModel.showLoading(false)
            onFailCallback?.let {
                onFailCallback.invoke(resource)
            } ?: run {
                viewModel.showErrorDialog(resource.error)
            }
        }
    }
}

// TODO
// use for Flow.zip, just handle success & error one time.
//fun handleResources(
//    viewModel: BaseViewModel,
//    resources: List<Resource<BaseData>>,
//    onSuccessCallback: (arrData: List<Resource<BaseData>>) -> Unit
//) {
//    Log.d("thong", "handleResource-start ${System.currentTimeMillis()}")
//    resources.firstOrNull { it is Resource.Error }?.run {
//        viewModel.showErrorDialog((this as Resource.Error).error)
//        Log.d("thong", "handleResource-Error ${System.currentTimeMillis()}")
//    } ?: run {
//        onSuccessCallback.invoke(resources)
//        Log.d("thong", "handleResource-Success ${System.currentTimeMillis()}")
//    }
//    viewModel.showLoading(false)
//}

