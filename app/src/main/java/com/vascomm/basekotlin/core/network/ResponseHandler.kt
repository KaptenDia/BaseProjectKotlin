package com.vascomm.basekotlin.util

import com.vascomm.basekotlin.data.model.ErrorResp
import com.vascomm.basekotlin.data.model.ResultResp
import com.vascomm.basekotlin.data.model.SuccessResp

object ResponseHandler {

    fun <T> checkResponse(
        response: ResultResp<T>,
        showDialogError: Boolean = true,
        checkSession: Boolean = true,
        onSuccess: ((SuccessResp<T>) -> Unit)? = null,
        onFailure: ((ErrorResp) -> Unit)? = null,
        actionDialogError: (() -> Unit)? = null
    ) {
        when (response) {
            is SuccessResp -> {
                onSuccess?.invoke(response)
            }
            is ErrorResp -> {
                onFailure?.invoke(response)

                // Handle invalid session
                if (response.code == ErrorCode.INVLD_SESS.name && checkSession) {
                    // TODO: Show session expired dialog and navigate to login
                    // Example: showSessionExpiredDialog()
                }

                // Show error dialog if needed
                if (showDialogError) {
                    // TODO: Show error dialog with message
                    // Example: showErrorDialog(response.message, actionDialogError)
                }
            }
        }
    }
}
