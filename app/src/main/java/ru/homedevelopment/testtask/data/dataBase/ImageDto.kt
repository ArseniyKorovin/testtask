package ru.homedevelopment.testtask.data.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageDto (
    @PrimaryKey val imageUrl: String
)