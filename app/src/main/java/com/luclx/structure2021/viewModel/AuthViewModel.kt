package com.luclx.structure2021.viewModel

import androidx.lifecycle.liveData
import com.luclx.structure2021.base.BaseViewModel
import com.luclx.structure2021.data.local.prefs.PreferenceHelper
import com.luclx.structure2021.data.repository.AuthRepository

class AuthViewModel(
    private val authRepository: AuthRepository
    val pref: PreferenceHelper
) : BaseViewModel() {

    fun loginWithToken(accessToken: String) = liveData(coroutineContext) {
        emit(Resource.loading(null))
        emit(executeWithRetry(times = 1) {
            authRepository.doLoginWithToken(accessToken)
        })
    }

    fun syncProfile(accessToken: String, request: SyncProfileRequest) = liveData(coroutineContext) {
        emit(Resource.loading(null))
        emit(executeWithRetry(times = 1) { authRepository.syncProfile(accessToken, request) })
    }
}
