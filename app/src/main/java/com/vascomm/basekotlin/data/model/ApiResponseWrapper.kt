package com.vascomm.basekotlin.data.model

import com.google.gson.annotations.SerializedName

/**
 * API Response Wrapper
 * Standard wrapper for all API responses
 */
data class ApiResponseWrapper<T>(
    @SerializedName("code")
    val code: String? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: T? = null,

    @SerializedName("errors")
    val errors: List<ErrorItemResp>? = null
)

