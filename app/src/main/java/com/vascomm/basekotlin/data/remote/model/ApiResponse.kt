package com.vascomm.basekotlin.data.model

import com.google.gson.annotations.SerializedName

// Base Response
sealed class ResultResp<out T> {
    abstract val code: String?
    abstract val message: String?
    abstract val errors: List<ErrorItemResp>?
}

// Success Response
data class SuccessResp<T>(
    @SerializedName("code")
    override val code: String? = "",
    @SerializedName("message")
    override val message: String? = "",
    @SerializedName("data")
    val data: T? = null,
    override val errors: List<ErrorItemResp>? = null
) : ResultResp<T>()

// Error Response
data class ErrorResp(
    @SerializedName("code")
    override val code: String? = "",
    @SerializedName("message")
    override val message: String? = "Request timeout",
    @SerializedName("errors")
    override val errors: List<ErrorItemResp>? = null
) : ResultResp<Nothing>()

// Error Item
data class ErrorItemResp(
    @SerializedName("field")
    val field: String? = null,
    @SerializedName("message")
    val message: String? = null
)
