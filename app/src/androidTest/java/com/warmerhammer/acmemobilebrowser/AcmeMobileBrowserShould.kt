package com.warmerhammer.acmemobilebrowser


import android.util.Log
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalComposeUiApi
@RunWith(AndroidJUnit4::class)
class AcmeMobileBrowserShould {

    @get:Rule
    val composeTestActivityRule = createAndroidComposeRule<MainActivity>()
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var navController: NavHostController

    @Test
    fun display_bookmarks_section_on_homescreen() {
        composeTestActivityRule.onNodeWithText("Explore Bookmarks").assertIsDisplayed()
    }

    @Test
    fun display_bookmarks_in_bookmark_section_of_homescreen() {
        composeTestActivityRule
            .onAllNodesWithContentDescription("Bookmark Trashcan")
            .assertCountEquals(5)
    }

    @Test
    fun successfully_delete_bookmark() {
        with(composeTestActivityRule) {
            onAllNodesWithContentDescription("Bookmark Trashcan")
                .onFirst()
                .performClick()

            onAllNodesWithContentDescription("Bookmark Trashcan")
                .assertCountEquals(4)

        }
    }

    @Test
    fun successfully_navigate_to_webview() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()

        }
    }

    @Test
    fun successfully_search_one_time_using_go_button() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()

            val search = "https://www.google.com/"

            onNodeWithTag("Search Bar TextField")
                .performTextClearance()

            onNodeWithTag("Search Bar TextField")
                .performTextInput(search)

            onNodeWithTag("Go Button")
                .performClick()

            for ((key, value) in onNodeWithTag("Search Bar TextField").fetchSemanticsNode().config) {
                if (key.name == "EditableText") {
                    assertEquals(search, value.toString())
                }
            }

        }
    }


    @Test
    fun successfully_search_one_time_using_ime_button() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()

            val search = "https://www.google.com/"

            onNodeWithTag("Search Bar TextField")
                .performTextClearance()

            onNodeWithTag("Search Bar TextField")
                .performTextInput(search)

            onNodeWithTag("Search Bar TextField")
                .performImeAction()


            for ((key, value) in onNodeWithTag("Search Bar TextField").fetchSemanticsNode().config) {
                if (key.name == "EditableText") {
                    assertEquals(search, value.toString())
                }
            }

        }
    }


    @Test
    fun successfully_search_three_times_using_go_button() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()


            val searches = listOf(
                "https://www.google.com/",
                "https://kotlinlang.org/",
                "https://www.yahoo.com/"
            )


            for (search in searches) {
                onNodeWithTag("Search Bar TextField")
                    .performTextClearance()

                onNodeWithTag("Search Bar TextField")
                    .performTextInput(search)

                onNodeWithTag("Search Bar TextField")
                    .performImeAction()
            }

            for ((key, value) in onNodeWithTag("Search Bar TextField").fetchSemanticsNode().config) {
                if (key.name == "EditableText") {
                    assertEquals("https://www.yahoo.com/", value.toString())
                }
            }

        }
    }

    @Test
    fun successfully_search_three_times_using_ime_button() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()


            val searches = listOf(
                "https://www.ask.com/",
                "https://www.google.com/",
                "https://www.yahoo.com/"
            )


            for (search in searches) {
                onNodeWithTag("Search Bar TextField")
                    .performTextClearance()

                onNodeWithTag("Search Bar TextField")
                    .performTextInput(search)

                onNodeWithTag("Search Bar TextField")
                    .performImeAction()
            }

            for ((key, value) in onNodeWithTag("Search Bar TextField").fetchSemanticsNode().config) {
                if (key.name == "EditableText") {
                    assertEquals("https://www.yahoo.com/", value.toString())
                }
            }

        }

    }

    @Test
    fun go_back_three_times() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()


            val searches = listOf(
                "https://www.ask.com/",
                "https://www.linkedin.com/",
                "https://www.yahoo.com/"
            )

            for (search in searches) {
                onNodeWithTag("Search Bar TextField")
                    .performTextClearance()

                onNodeWithTag("Search Bar TextField")
                    .performTextInput(search)

                onNodeWithTag("Search Bar TextField")
                    .performImeAction()

                waitForIdle()
            }

            for (search in searches) {
                onNodeWithContentDescription("back")
                    .performClick()

                waitForIdle()
            }

            for ((key, value) in onNodeWithTag("Search Bar TextField").fetchSemanticsNode().config) {
                if (key.name == "EditableText") {
                    assertEquals("https://neeva.com/", value.toString())
                }
            }
        }
    }


    @Test
    fun go_back_three_go_forward_three_times() {
        with(composeTestActivityRule) {
            onAllNodesWithTag("Bookmark Item")
                .onFirst()
                .performClick()

            onNodeWithTag("Webview")
                .assertIsDisplayed()


            val searches = listOf(
                "https://www.ask.com/",
                "https://www.orange.com/",
                "https://www.google.com/"
            )

            for (search in searches) {
                onNodeWithTag("Search Bar TextField")
                    .performTextClearance()

                onNodeWithTag("Search Bar TextField")
                    .performTextInput(search)

                onNodeWithTag("Search Bar TextField")
                    .performImeAction()
                waitForIdle()
            }

            for (search in searches) {
                onNodeWithContentDescription("back")
                    .performClick()
                waitForIdle()
            }

            for (search in searches) {
                onNodeWithContentDescription("forward")
                    .performClick()
                waitForIdle()
            }

            for ((key, value) in onNodeWithTag("Search Bar TextField").fetchSemanticsNode().config) {
                if (key.name == "EditableText") {
                    assertEquals("https://www.google.com/", value.toString())
                }
            }
        }
    }
}