package com.sunsandsports.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.appname.structure.base.ViewModelBase
import com.google.android.material.snackbar.Snackbar
import com.kotlinusermodule.network.model.HttpErrorCode
import com.sunsandsports.R
import com.sunsandsports.ui.ActivitySplash

/**
 * This is the Abstract Base Fragment will used for defining the Base level Fragment Features.
 * It will manage the Databinding feature so that we are not required to call the same function
 * of Inflating the View into Databinding object.
 * I also defined the some other features which will be used for different fragments and
 * no need to manage at each fragment by wrting the same code multiple time.
 */
abstract class FragmentBase<V : ViewModelBase, DataBinding : ViewDataBinding> :
    FragmentBaseWrapper() {
    private var _activity: ActivitySplash? = null
    private var viewModel: V? = null
    private lateinit var dataBinding: DataBinding

    /**
     * This is the abstract method by which we are generating the
     * Databinding with View from Single Screen.
     *
     */
    abstract fun getLayoutId(): Int

    /**
     * This is the generic method where we have to setup the toolbar from single place and
     * from all other extended fragment should have to manage the logic related to the toolbar
     * from this method
     */
    abstract fun setupToolbar()

    /**
     * This is the method from where we are initialized the
     * fragment specific data members and methods.
     */
    abstract fun initializeScreenVariables()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        return dataBinding.root
    }

    private fun handleException(code: Int, message: String, messageCode: String) {
        when (code) {
            HttpErrorCode.EMPTY_RESPONSE.code -> dataNotFound(message, messageCode)
            HttpErrorCode.NEW_VERSION_FOUND.code -> newVersionFound()
            HttpErrorCode.UNAUTHORIZED.code -> unAuthorizationUser(message, messageCode)
            HttpErrorCode.NO_CONNECTION.code -> noInternet()
            else -> somethingWentWrong()
        }
    }

    fun getDataBinding() = dataBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ViewModel is set as Binding Variable
        dataBinding.apply {
            lifecycleOwner = this@FragmentBase
        }
        setActivity(activity as ActivitySplash)
        setUpHideKeyBoard()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        initializeScreenVariables()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
        setUpSnackbar()
        setUpProgressBar()
    }


    /**
     * This is generic method based on the MutableLive Data Concept where value changed with Progressbar
     * will reflect and decide if value is true then it will display.
     */
    private fun setUpProgressBar() {
        viewModel?.getProgressBar()?.observe(
            this,
            Observer { t: Boolean ->
                if (_activity != null) {
                    _activity?.displayProgress(t)
                }
            }
        )
    }

    /**
     * This is the abstract method where we are adding the logic for generating the ViewModel
     * Object.
     */
    abstract fun getViewModel(): V?

    /**
     * This is generic method based on the MutableLive Data Concept where value changed with Snakebar
     * will reflect and display the message with Snakebar.
     */
    private fun setUpSnackbar() {
        viewModel?.getSnakeBarMessage()?.observe(
            this,
            Observer { o: Any ->
                if (_activity != null) {
                    if (o is Int) {
                        _activity?.resources?.getString(o)?.let { showSnackbar(it) }!!
                    } else if (o is String) {
                        showSnackbar(o)
                    }
                }
            } as Observer<Any>
        )
    }

    /**
     * This is generic method based on the MutableLive Data Concept where value changed with Keyboard
     * will reflect and decide if value is false then it will hide the Keyboard.
     */
    private fun setUpHideKeyBoard() {
        viewModel?.getHideKeyBoardEvent()
            ?.observe(viewLifecycleOwner, Observer { hideKeyboard() })
    }

    override fun onDetach() {
        super.onDetach()
        _activity = null
    }

    fun setActivity(activity: ActivitySplash) {
        this._activity = activity
    }

    fun getBaseActivity(): ActivitySplash? {
        return _activity
    }

    fun hideKeyboard() {
        if (_activity != null) {
            _activity?.hideKeyboard()
        }
    }

    /**
     * This method is used to display the Snake Bar Toast message to user.
     *
     * @param message string to display.
     */
    fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(
            _activity?.findViewById(android.R.id.content)!!,
            message,
            Snackbar.LENGTH_SHORT
        )
        val view = snackbar.view
        val snackTV = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackTV.maxLines = 5
//        snackTV.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        snackbar.show()
    }

    /**
     * This is the generic method from where the message will display if something went wrong.
     * This will called based from the make Api called and decide the response with response code.
     */
    override fun somethingWentWrong() {
        viewModel?.showSnackbarMessage(R.string.label_response_error)
    }

    /**
     * This is the generic method from where the message will display if Internet connection not available.
     * This will called based from the make Api called and decide the response with response code.
     */
    override fun noInternet() {
        viewModel?.showSnackbarMessage(R.string.no_internet)
    }

    override fun onRetryClicked(retryButtonType: String) {
    }

    /**
     * This is the generic method from where the message will display if No Data Found.
     * This will called based from the make Api called and decide the response with response code.
     */
    override fun dataNotFound(message: String?, messageCode: String?) {
        message?.let { viewModel?.showSnackbarMessage(it) }
    }

    /**
     * This is the generic method from where the message will display if user verified or not.
     * This will called based from the make Api called and decide the response with response code.
     */
    override fun verifyUser(message: String) {
        viewModel?.showSnackbarMessage(message)
    }

    override fun newVersionFound() {
    }

    /**
     * This is the generic method from where the message will display if user is unauthorized.
     * This will called based from the make Api called and decide the response with response code.
     */
    override fun unAuthorizationUser(message: String?, messageCode: String?) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getBaseActivity()?.displayProgress(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
    }
}
