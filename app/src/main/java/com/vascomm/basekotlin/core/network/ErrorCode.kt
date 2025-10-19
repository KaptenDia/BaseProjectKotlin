package com.vascomm.basekotlin.util

enum class ErrorCode(val message: String) {
    SUCCESS("Success"),
    DUP_DATA("Data duplicated"),
    NO_CHANGE("No data changed"),
    EXIST_DATA("Data already exists"),
    CRED_ERROR("Credential mismatch"),
    DATA_LARGE("Data too large"),
    PAY_FAIL("Payment failed"),
    INVLD_TRAN("Invalid transaction"),
    BLOCKED("User or data blocked"),
    REJ_DATA("Data rejected"),
    EXPIRED("Expired"),
    MISS_KEY("Missing key"),
    EXT_API_ERR("Third party API failure"),
    MAX_LIMIT("Reached max limit"),
    PROG_ERR("Program code error"),
    ERR_INPT("Validation Error"),
    INVLD_SESS("Invalid Session"),
    MTHD_ERR("Method Not Allowed");

    companion object {
        fun fromCode(code: String?): ErrorCode? {
            return entries.find { it.name == code }
        }
    }
}
