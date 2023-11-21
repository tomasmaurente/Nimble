package com.example.nimble

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ReservationUseCaseTest {

    @RelaxedMockK
    private lateinit var reservationRepository: ReservationRepository
    private lateinit var reservationUseCase: ReservationUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        reservationUseCase = ReservationUseCase(reservationRepository)
    }

    @Test
    fun getReservationListFromDataBase() = runBlocking {
        coEvery { reservationRepository.getReservationList(true) } returns Result.Success(ReservationListModel(listOf()))

        reservationUseCase(true)

        coVerify (exactly = 1 ) { reservationRepository.getReservationList(true) }
    }

    @Test
    fun getReservationListFromService() = runBlocking {
        coEvery { reservationRepository.getReservationList(false) } returns Result.Success(ReservationListModel(listOf()))

        reservationUseCase(false)

        coVerify (exactly = 1 ) { reservationRepository.getReservationList(false) }
    }
}
