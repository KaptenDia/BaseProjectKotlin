package com.vascomm.basekotlin.util

object ResponseCode {
    const val SUCCESS = 200
    const val CREATED = 201
    const val ACCEPTED = 202
    const val NO_CONTENT = 204
    const val BAD_REQUEST = 400
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val INTERNAL_SERVER_ERROR = 500
    const val DATA_UPDATED = 200

    fun getMessage(code: Int): String {
        return when (code) {
            SUCCESS -> "Success"
            CREATED -> "Created"
            ACCEPTED -> "Accepted"
            NO_CONTENT -> "No Content"
            BAD_REQUEST -> "Bad Request"
            UNAUTHORIZED -> "Unauthorized"
            FORBIDDEN -> "Forbidden"
            NOT_FOUND -> "Not Found"
            INTERNAL_SERVER_ERROR -> "Internal Server Error"
            DATA_UPDATED -> "Data Updated"
            else -> "Unknown Response"
        }
    }
}
