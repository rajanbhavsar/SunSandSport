package com.sunsandsports.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.appname.structure.base.ViewModelBase
import com.sunsandsports.R


/**
 * This is the Abstract Base Activity to define some common features that may be required
 * in terms of Extending the Feature and use in future purpose
 */
abstract class ActivityBase<V : ViewModelBase> : AppCompatActivity() {
    val viewModel by viewModels<ViewModelBase>()
    lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpHideKeyBoard()
    }

    /**
     * This method is used to identify the hide Keyboard Event Whenever user update this Live Data
     *  Object it will call and hide the Keyboard.
     */
    private fun setUpHideKeyBoard() {
        viewModel.getHideKeyBoardEvent().observe(this, Observer { hideKeyboard() })
    }

    /**
     * This method is used to hide the keyboard.
     */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * This method is used for Navigating from One Screen to Next Screen using Navigation
     * Direction graph.
     * @param navigationId This is the Id of the Navigation Graph Action
     */
    fun navigateToNextScreen(navigationId: Int) {
        try {
            navHostFragment.navController.navigate(navigationId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * This is the Method to initialize the variable at base level for Navigating from Single Class.
     * @param navHostFragment This is the Id of the NavHost Fragment or  FragmentContainerView.
     */
    fun setNavaigationHostFragment(navHostFragment: NavHostFragment) {
        this.navHostFragment = navHostFragment
    }

    /**
     * This method is used for getting the current Fragment Instance that is active.
     *
     */
    fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.splash_host)
    }
}
