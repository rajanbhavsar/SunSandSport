package com.sunsandsports.listener

import com.sunsandsports.ui.User

/**
 * This is the Click listener to manage the click event on Item of the user List and call the detail page.
 *
 */
interface clickListener {
    fun onUserItemClick(model: User)
}