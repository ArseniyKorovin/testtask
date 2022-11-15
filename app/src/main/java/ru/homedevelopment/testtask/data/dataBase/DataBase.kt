package ru.homedevelopment.testtask.data.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        ImageDto::class
    ]
)
abstract class DataBase : RoomDatabase() {

    abstract fun getImageDao() : ImageDao
}