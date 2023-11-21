package com.example.data.local_data_base.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservationList")
data class ReservationRoom(
    @PrimaryKey
    val id: String,
    val authorizationCode: String,
    val start: Long,
    val end: Long,
    val parkingLot: Int
)