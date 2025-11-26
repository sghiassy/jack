package com.shaheenghiassy.jack.ui.mainview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.shaheenghiassy.jack.data.models.CounterModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Robolectric test for MainView Composable.
 * Tests the UI rendering and user interactions with the counter view.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], manifest = Config.NONE)
class MainViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: MainViewModel

    @Before
    fun setUp() {
        mockViewModel =
            mockk(relaxed = true) {
                every { increment() } just Runs
                every { decrement() } just Runs
            }
    }

    @Test
    fun `should display Loading text when UI state is Loading`() {
        // Given

        // When
        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = MainViewUIState.Loading,
                viewModel = mockViewModel,
            )
        }

        // Then
        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()
    }

    @Test
    fun `should display counter value when UI state is Loaded`() {
        // Given
        val counterValue = 42
        val loadedState = MainViewUIState.Loaded(CounterModel(counterValue))

        // When
        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // Then
        composeTestRule.onNodeWithText(counterValue.toString()).assertIsDisplayed()
    }

    @Test
    fun `should display default counter value 420 when UI state is Loaded with default model`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel())

        // When
        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // Then
        composeTestRule.onNodeWithText("420").assertIsDisplayed()
    }

    @Test
    fun `should display Increment and Decrement buttons`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel(10))

        // When
        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // Then
        composeTestRule.onNodeWithText("Increment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Decrement").assertIsDisplayed()
    }

    @Test
    fun `should call viewModel increment when Increment button is clicked`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel(10))

        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // When
        composeTestRule.onNodeWithText("Increment").performClick()

        // Then
        verify(exactly = 1) { mockViewModel.increment() }
    }

    @Test
    fun `should call viewModel decrement when Decrement button is clicked`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel(10))

        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // When
        composeTestRule.onNodeWithText("Decrement").performClick()

        // Then
        verify(exactly = 1) { mockViewModel.decrement() }
    }

    @Test
    fun `should call viewModel increment multiple times when button clicked multiple times`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel(5))

        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // When
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithText("Increment").performClick()

        // Then
        verify(exactly = 3) { mockViewModel.increment() }
    }

    @Test
    fun `should handle both increment and decrement clicks in sequence`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel(100))

        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // When
        composeTestRule.onNodeWithText("Increment").performClick()
        composeTestRule.onNodeWithText("Decrement").performClick()
        composeTestRule.onNodeWithText("Increment").performClick()

        // Then
        verify(exactly = 2) { mockViewModel.increment() }
        verify(exactly = 1) { mockViewModel.decrement() }
    }

    @Test
    fun `should display Empty text when UI state is Empty`() {
        // Given

        // When
        composeTestRule.setContent {
            MainView(
                innerPadding = PaddingValues(0.dp),
                uiState = MainViewUIState.Empty,
                viewModel = mockViewModel,
            )
        }

        // Then
        composeTestRule.onNodeWithText("Empty").assertIsDisplayed()
    }

    @Test
    fun `should respect provided innerPadding`() {
        // Given
        val loadedState = MainViewUIState.Loaded(CounterModel(25))
        val customPadding = PaddingValues(all = 16.dp)

        // When
        composeTestRule.setContent {
            MainView(
                innerPadding = customPadding,
                uiState = loadedState,
                viewModel = mockViewModel,
            )
        }

        // Then - View should render without crashing and display content
        composeTestRule.onNodeWithText("25").assertIsDisplayed()
        composeTestRule.onNodeWithText("Increment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Decrement").assertIsDisplayed()
    }
}
