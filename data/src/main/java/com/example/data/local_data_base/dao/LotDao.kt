package com.example.data.local_data_base.dao

import androidx.room.*
import com.example.data.local_data_base.entities.LotRoom

@Dao
interface LotDao {

    @Query("SELECT * FROM lotList")
    suspend fun findLotList(): List<LotRoom>?

    @Query("SELECT * FROM lotList WHERE parkingId = :parkingId")
    suspend fun findByParkingLot(parkingId: Int): LotRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewLot(lot: LotRoom)

}