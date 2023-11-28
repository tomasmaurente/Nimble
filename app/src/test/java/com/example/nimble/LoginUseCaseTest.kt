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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
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
    fun worksCorrectly() = runBlocking {
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
    fun notAceptEmail() = runBlocking {
        val loginParameters = LoginRequest(
            email = "tes",
            password = "password123",
            client_id = "123",
            client_secret = "123",
            grant_type = "123"
        )
        val expectedResponse = LoginResponse(error_message = ErrorHandler.PasswordError.errorMessage)

        coEvery { loginRepo.loginRequest(loginParameters) } returns expectedResponse

        val result = loginUseCase(loginParameters)

        assertEquals(expectedResponse.error_message, result.error_message)
        coVerify (exactly = 0 ) {loginRepo.loginRequest(loginParameters)}
    }

    @Test
    fun notAceptPassword() = runBlocking {
        val loginParameters = LoginRequest(
            email = "test@gmail.com",
            password = "pass",
            client_id = "123",
            client_secret = "123",
            grant_type = "123"
        )
        val expectedResponse = LoginResponse(error_message = ErrorHandler.PasswordError.errorMessage)

        coEvery { loginRepo.loginRequest(loginParameters) } returns expectedResponse

        val result = loginUseCase(loginParameters)

        assertEquals(expectedResponse.error_message, result.error_message)
        coVerify (exactly = 0 ) {loginRepo.loginRequest(loginParameters)}
    }
}