package com.sunsandsports.base

import androidx.fragment.app.Fragment
import com.kotlinusermodule.network.model.HttpErrorCode

/**
 * This is the Base Wrapper Fragment it means if we have to defined the features like No Internet functionality.
 * With Each Of the Fragments then we just have to implement that Method and we can used that one individually with Fragment Level
 *
 */
abstract class FragmentBaseWrapper : Fragment() {
    abstract fun somethingWentWrong()

    abstract fun noInternet()

    abstract fun onRetryClicked(retryButtonType: String)

    abstract fun dataNotFound(message: String?, messageCode: String?)

    abstract fun verifyUser(message: String)

    abstract fun newVersionFound()

    abstract fun unAuthorizationUser(message: String?, messageCode: String?)

    open fun httpFailedHandler(code: Int, message: String?, messageCode: String?) {
        handleException(code, message, messageCode)
    }

    private fun handleException(code: Int, message: String?, messageCode: String?) {
        when (code) {
            HttpErrorCode.EMPTY_RESPONSE.code -> dataNotFound(message, messageCode)
            HttpErrorCode.NEW_VERSION_FOUND.code -> newVersionFound()
            HttpErrorCode.UNAUTHORIZED.code -> unAuthorizationUser(message, messageCode)
            HttpErrorCode.NO_CONNECTION.code -> noInternet()
            else -> somethingWentWrong()
        }
    }
}
