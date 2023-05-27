package ir.enigma.app.unit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import ir.enigma.app.data.ApiResult
import ir.enigma.app.ui.ApiViewModel

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class ApiViewModelTest {

    lateinit var apiViewModel: ApiViewModel<Double>

    @Before
    fun setup() {                                                       // Arrange for All tests
        apiViewModel = ApiViewModel()
    }

    // ApiViewModel method tests
    @Test
    fun `startLading should set state to loading`() {                  // Test loading state
        apiViewModel.startLading()                                      // Act
        assert(apiViewModel.state.value is ApiResult.Loading)           // Assert
    }

    @Test
    fun `error should set state to error and set message`() {                                                // Test error state
        apiViewModel.error("test")                                      // Act
        assert(apiViewModel.state.value is ApiResult.Error)             // Asserts
        assert((apiViewModel.state.value as ApiResult.Error).message == "test")
    }

    @Test
    fun `success should set state to success and set data`() {                                               // Test success state
        apiViewModel.success(2.2)                                   // Act
        assert(apiViewModel.state.value is ApiResult.Success)           // Assert
        assert((apiViewModel.state.value as ApiResult.Success).data == 2.2)
    }

}