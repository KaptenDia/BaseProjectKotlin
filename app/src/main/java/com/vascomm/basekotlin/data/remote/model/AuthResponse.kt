package com.vascomm.basekotlin.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    @Expose val token: String? = null,
    @SerializedName("message")
    @Expose
    private val message: Int? = null,
)
