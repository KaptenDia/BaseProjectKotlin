package com.vascomm.basekotlin.core.network

data class ErrorResponse(
    val code: String?,
    val message: String?,
    val errors: List<FieldError>?
)

data class FieldError(
    val field: String?,
    val message: String?
)
