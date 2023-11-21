package com.example.nimble

import com.example.domain.utils.AddPossibilities
import org.junit.Before
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class AddUseCaseTest{

    @RelaxedMockK
    private lateinit var addRepository: AddRepository

    lateinit var addUseCase: AddUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        addUseCase = AddUseCase(addRepository)
    }

    @Test
    fun parametersAreCorrect() = runBlocking {
        var reservation = Reservation("",0,1,"",0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf()))

        assert( result == AddPossibilities.Successful)
        coVerify (exactly = 1 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun parkingLotParameterIsIncorrect() = runBlocking {
        var reservation = Reservation("",0,1,"",-1)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf()))

        assert( result == AddPossibilities.IncorrectParameters)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }


    @Test
    fun startDateIsEqualToEndDate() = runBlocking {
        var reservation = Reservation("",0,0,"",0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf()))

        assert( result == AddPossibilities.IncorrectDates)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun startDateIsPreviousToEndDate() = runBlocking {
        var reservation = Reservation("",1,0,"",0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf()))

        assert( result == AddPossibilities.IncorrectDates)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }


    @Test
    fun newReservationIsPlacedAfterPreviousReservations() = runBlocking {
        var reservation = Reservation("",6,7,"",0)
        var reservation1 = ReservationModel("","",0,1,0)
        var reservation2 = ReservationModel("","",2,3,0)
        var reservation3 = ReservationModel("","",4,5,0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1,reservation2,reservation3)))

        assert( result == AddPossibilities.Successful)
        coVerify (exactly = 1 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun newReservationIsPlacedBeforePreviousReservations() = runBlocking {
        var reservation = Reservation("",0,1,"",0)
        var reservation2 = ReservationModel("","",2,3,0)
        var reservation3 = ReservationModel("","",4,5,0)
        var reservation1 = ReservationModel("","",6,7,0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1,reservation2,reservation3)))

        assert( result == AddPossibilities.Successful)
        coVerify (exactly = 1 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun newReservationIsPlacedBetweenPreviousReservations() = runBlocking {
        var reservation = Reservation("",4,5,"",0)

        var reservation1 = ReservationModel("","",6,7,0)
        var reservation2 = ReservationModel("","",2,3,0)

        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1,reservation2)))

        assert( result == AddPossibilities.Successful)
        coVerify (exactly = 1 ) { addRepository.addReservation(reservation) }
    }

    // Case a
    @Test
    fun otherReservationMatchEndDateWithTheNewOnesStartDate() = runBlocking {
        var reservation = Reservation("",3,4,"",0)
        var reservation1 = ReservationModel("","",2,3,0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1)))

        assert( result == AddPossibilities.Occupied)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }

    // Case b
    @Test
    fun otherReservationMatchesInsideWithTheNewOne() = runBlocking {
        var reservation = Reservation("",3,4,"",0)
        var reservation1 = ReservationModel("","",1,5,0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1)))

        assert( result == AddPossibilities.Occupied)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }

    // Case c
    @Test
    fun otherReservationMatchStartDateWithTheNewOnesEndDate() = runBlocking {
        var reservation = Reservation("",0,2,"",0)
        var reservation1 = ReservationModel("","",2,3,0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1)))

        assert( result == AddPossibilities.Occupied)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun otherReservationMatchesAllOutsideTheNewOne() = runBlocking {
        var reservation = Reservation("",1,5,"",0)
        var reservation1 = ReservationModel("","",2,4,0)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, ReservationListModel(listOf(reservation1)))

        assert( result == AddPossibilities.Occupied)
        coVerify (exactly = 0 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun conectionToServiceFail() = runBlocking {
        var reservation = Reservation("",0,1,"",1)
        coEvery { addRepository.addReservation(reservation) } returns Result.Failure(Exception())

        var result = addUseCase(reservation, ReservationListModel(listOf()))

        assert( result == AddPossibilities.Fail)
        coVerify (exactly = 1 ) { addRepository.addReservation(reservation) }
    }

    @Test
    fun nullIncomingList() = runBlocking {
        var reservation = Reservation("",0,1,"",1)
        coEvery { addRepository.addReservation(reservation) } returns Result.Success(true)

        var result = addUseCase(reservation, null)

        assert( result == AddPossibilities.Successful)
        coVerify (exactly = 1 ) { addRepository.addReservation(reservation) }
    }


}