package com.example.data.local_data_base.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lotList")
data class LotRoom(
    @PrimaryKey
    val parkingId: Int
)