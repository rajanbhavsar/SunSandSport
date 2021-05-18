package com.appname.structure.base

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * This is the Base videmodel to define and initialize the Some usedful Live Data and Concepts
 * that is used in each of the viewmodel like coroutineContext, Progress bar, Snakebar etc.,
 */
open class ViewModelBase : ViewModel() {

    private val job = SupervisorJob()
    val coroutineContext = Dispatchers.Main + job
    var snackbarMessage = MutableLiveData<Any>()
    var progressBarDisplay = MutableLiveData<Boolean>(false)
    private var hideKeyBoardEvent = MutableLiveData<Any>()

    /**
     * Cancel the job when the view model is destroyed
     */
    override fun onCleared() {
        try {
            super.onCleared()
            coroutineContext.cancel()
        } catch (e: Exception) {
            Log.e("data", "data")
        }
    }

    fun getSnakeBarMessage(): MutableLiveData<Any> {
        return snackbarMessage
    }

    fun getProgressBar(): MutableLiveData<Boolean> {
        return progressBarDisplay
    }

    fun getHideKeyBoardEvent(): MutableLiveData<Any> {
        return hideKeyBoardEvent
    }

    /**
     * This method is used to display the Snake Bar Message
     *
     * @param message string object to display.
     */
    fun showSnackbarMessage(message: Any) {
        snackbarMessage.value = message
    }

    fun showProgressBar(display: Boolean) {
        progressBarDisplay.value = display
    }

    fun hideKeyboard() {
        getHideKeyBoardEvent().value = true
    }

    /**
     * This is generic method to hide the keyboard when user clicked outside.
     */
    fun setupOutSideTouchHideKeyboard(view: View, mContext: Context) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {

            view.setOnTouchListener { v, _ ->
                val mgr =
                    mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                mgr.hideSoftInputFromWindow(v.windowToken, 0)
                false
            }
        }

        // If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {

            for (i in 0 until view.childCount) {

                val innerView = view.getChildAt(i)

                setupOutSideTouchHideKeyboard(innerView, mContext)
            }
        }
    }
}
