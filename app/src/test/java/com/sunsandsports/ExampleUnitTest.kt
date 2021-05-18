package com.sunsandsports

import com.sunsandsports.utils.Validator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {


    /**
     *
     * This is the Test case where i am checking the given input is empty or not.
     *
     */
    @Test
    fun whenInputIsValid() {
        val userImage = "Raj"
        val userName = "Rajan Bhavsar"
        val result = Validator.validateInput(userImage, userName)
        assertEquals(result, true)
    }
}