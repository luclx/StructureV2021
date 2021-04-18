package com.luclx.structure2021.data.model.response

data class AuthStatusResponse(
    val area_code: String,
    val email_address: String,
    val full_name: String,
    val id: String,
    val image_url: String?,
    val mobile_number: String,
    val password: String
) : BaseResponse()