package com.example.nimble


import com.example.nimble.data.repositories.LoginRepositoryImp
import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.usecases.LoginUseCase
import com.example.nimble.domain.utils.ErrorHandler
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.jupiter.api.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LoginUseCaseTest {

    @RelaxedMockK
    private lateinit var loginUseCase: LoginUseCase

    private lateinit var loginRepo: LoginRepositoryImp

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        loginRepo = mockk()
        loginUseCase = LoginUseCase(loginRepo)
    }

    @Test
    fun ola() = runBlocking {
        val loginParameters = LoginRequest(
            email = "test@example.com",
            password = "password123",
            client_id = "123",
            client_secret = "123",
            grant_type = "123"
        )
        val expectedResponse = LoginResponse()

        coEvery { loginRepo.loginRequest(loginParameters) } returns expectedResponse

        val result = loginUseCase(loginParameters)

        assertEquals(expectedResponse, result)
        coVerify (exactly = 1 ) {loginRepo.loginRequest(loginParameters)}
    }

    @Test
    fun invalid() = runBlocking {
        val loginParameters = LoginRequest(
            email = "test@example.com",
            password = "123",
            client_id = "123",
            client_secret = "123",
            grant_type = "123"
        )
        val expectedResponse = LoginResponse(error_message = ErrorHandler.PasswordError.errorMessage)

        whenever(loginRepo.loginRequest(loginParameters)).thenReturn(null)

        val result = loginUseCase(loginParameters)

        assertEquals(expectedResponse, result)
        verify(loginRepo, times(1)).loginRequest(loginParameters)
    }

    @Test
    fun login() = runBlocking {
        val loginParameters = LoginRequest(
            email = "test",
            password = "123456456",
            client_id = "123",
            client_secret = "123",
            grant_type = "123"
        )
        val expectedResponse = LoginResponse(error_message = ErrorHandler.PasswordError.errorMessage)

        whenever(loginRepo.loginRequest(loginParameters)).thenReturn(null)

        val result = loginUseCase(loginParameters)

        assertEquals(expectedResponse, result)
        verify(loginRepo, times(1)).loginRequest(loginParameters)
    }

}