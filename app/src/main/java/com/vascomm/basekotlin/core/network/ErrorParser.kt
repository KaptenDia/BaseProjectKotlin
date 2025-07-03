package com.vascomm.basekotlin.core.network

import com.google.gson.Gson
import okhttp3.ResponseBody

object ErrorParser {
    fun parse(errorBody: ResponseBody?): ErrorResponse {
        return try {
            Gson().fromJson(errorBody?.string(), ErrorResponse::class.java)
        } catch (e: Exception) {
            ErrorResponse(null, null, null)
        }
    }
}
