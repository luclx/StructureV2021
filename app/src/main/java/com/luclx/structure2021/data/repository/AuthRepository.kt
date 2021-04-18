package com.luclx.structure2021.data.repository

import com.luclx.structure2021.data.Resource
import com.luclx.structure2021.data.apiService.ApiService
import com.luclx.structure2021.data.model.request.SyncProfileRequest
import com.luclx.structure2021.data.model.response.AuthStatusResponse
import com.luclx.structure2021.utils.handleRequest

class AuthRepository(private val apis: ApiService) {

    suspend fun doLoginWithToken(accessToken: String): Resource<AuthStatusResponse> =
        handleRequest { apis.loginWithToken(accessToken) }

    suspend fun syncProfile(
        accessToken: String,
        request: SyncProfileRequest
    ) = handleRequest {
        apis.authSync("application/json", accessToken, request)
    }

}
