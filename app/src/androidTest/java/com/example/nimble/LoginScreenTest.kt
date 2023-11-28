package com.example.nimble

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nimble.view.composeViews.LoginButton
import com.example.nimble.view.composeViews.TestTags.login_btn
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LandingScreenTest (){

    @get:Rule
    val composeTestRule = createComposeRule()



    @Test
    fun logInButtonTest() {
        var showSnack = false
        composeTestRule.setContent {
            LoginButton { showSnack = true }
        }
        assertTrue(!showSnack)
        composeTestRule.onNodeWithTag(login_btn).performClick()
        assertTrue(true)

    }

    @Test
    fun basicTest() {
        composeTestRule.onNodeWithText("Forgot?").assertIsDisplayed()
    }
}