package com.luclx.structure2021.data.model.request

data class SyncProfileRequest(
    val device_id: String,
    val device_fcm_token: String
)