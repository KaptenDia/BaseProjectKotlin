package com.vascomm.basekotlin.core.network

enum class ErrorCode(val code: String, val message: String) {
    DUP_DATA("DUP_DATA", "Data duplicated"),
    NO_CHANGE("NO_CHANGE", "No data changed"),
    EXIST_DATA("EXIST_DATA", "Data already exists"),
    CRED_ERROR("CRED_ERROR", "Credential mismatch"),
    DATA_LARGE("DATA_LARGE", "Data too large"),
    PAY_FAIL("PAY_FAIL", "Payment failed"),
    INV_TRANS("INV_TRANS", "Invalid transaction"),
    BLOCKED("BLOCKED", "User or data blocked"),
    REJ_DATA("REJ_DATA", "Data rejected"),
    EXPIRED("EXPIRED", "Expired"),
    MISS_KEY("MISS_KEY", "Missing key"),
    EXT_API_ERR("EXT_API_ERR", "Third party API failure"),
    MAX_LIMIT("MAX_LIMIT", "Reached max limit"),
    PROG_ERR("PROG_ERR", "Program code error");

    companion object {
        fun getMessage(code: String?): String {
            return entries.firstOrNull { it.code == code }?.message ?: "Unknown error"
        }
    }
}
