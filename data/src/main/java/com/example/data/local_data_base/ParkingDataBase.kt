package com.example.data.local_data_base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local_data_base.dao.LotDao
import com.example.data.local_data_base.dao.ReservationDao
import com.example.data.local_data_base.entities.LotRoom
import com.example.data.local_data_base.entities.ReservationRoom

@Database( entities = [LotRoom::class,ReservationRoom::class], version = 1)
abstract class ParkingDataBase : RoomDatabase() {
    companion object{

        private const val DATABASE_NAME = "parkingDataBase"
        private lateinit var instance: ParkingDataBase

        @Synchronized
        fun getInstance( context: Context): ParkingDataBase{
            if(!this::instance.isInitialized) {
                instance = Room.databaseBuilder(context.applicationContext,
                                                ParkingDataBase::class.java,
                                                DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
    abstract fun lotDataBaseDao(): LotDao
    abstract fun reservationDataBaseDao(): ReservationDao
}