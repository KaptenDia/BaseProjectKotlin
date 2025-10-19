package com.vascomm.basekotlin.util

import com.google.gson.Gson
import com.vascomm.basekotlin.data.model.ApiResponseWrapper
import com.vascomm.basekotlin.data.model.ErrorResp
import com.vascomm.basekotlin.data.model.ResultResp
import com.vascomm.basekotlin.data.model.SuccessResp
import retrofit2.Response

object ResponseParser {

    val gson = Gson()

    inline fun <reified T> parseApiResponse(response: Response<ApiResponseWrapper<T>>): ResultResp<T> {
        return try {
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string()
                return if (errorBody != null) {
                    try {
                        val wrapper = gson.fromJson(errorBody, ApiResponseWrapper::class.java)
                        ErrorResp(
                            code = wrapper.code ?: response.code().toString(),
                            message = wrapper.message ?: response.message(),
                            errors = wrapper.errors
                        )
                    } catch (e: Exception) {
                        ErrorResp(
                            code = response.code().toString(),
                            message = response.message()
                        )
                    }
                } else {
                    ErrorResp(
                        code = response.code().toString(),
                        message = response.message()
                    )
                }
            }

            // Parse successful response
            val wrapper = response.body()
            if (wrapper == null) {
                return ErrorResp(
                    code = ErrorCode.PROG_ERR.name,
                    message = "Response body is null"
                )
            }

            // Check if response is successful based on code
            val code = wrapper.code
            if (code == "SUCCESS" || code == ErrorCode.SUCCESS.name) {
                return SuccessResp(
                    code = code,
                    message = wrapper.message,
                    data = wrapper.data
                )
            } else {
                // Response has error code
                return ErrorResp(
                    code = code ?: "UNKNOWN",
                    message = wrapper.message ?: "Unknown error",
                    errors = wrapper.errors
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ErrorResp(
                code = ErrorCode.PROG_ERR.name,
                message = "An error occurred while parsing the response: ${e.message}"
            )
        }
    }
}
