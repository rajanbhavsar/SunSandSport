package com.sunsandsports.utils

/**
 * i managed this class to learn the Unit Testing by calling from the ExampleInstrumentedTest
 */
object Validator {

    fun validateInput(userImage: String, userName: String): Boolean {
        return !(userImage.isEmpty() || userName.isEmpty())
    }
}