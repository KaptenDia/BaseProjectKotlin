package com.vascomm.basekotlin.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: UserData? = null
)

data class UserData(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("lastname")
    val lastname: String? = null
)
