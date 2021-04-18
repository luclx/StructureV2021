package com.luclx.structure2021.data.apiService

import com.luclx.structure2021.data.model.request.SyncProfileRequest
import com.luclx.structure2021.data.model.response.AuthStatusResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ApiService {
    @PUT("auth/sync-profile")
    suspend fun authSync(
        @Header("Content-Type") type: String,
        @Header("Authorization") accessToken: String,
        @Body request: SyncProfileRequest
    )

    @GET("auth/status")
    suspend fun loginWithToken(
        @Header("Authorization") accessToken: String
    ): AuthStatusResponse
}
