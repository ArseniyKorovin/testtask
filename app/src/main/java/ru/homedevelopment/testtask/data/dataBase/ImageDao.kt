package ru.homedevelopment.testtask.data.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageDao {

    @Insert(entity = ImageDto::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun setImagesUrls(imagesUrls: List<ImageDto>)

    @Query("SELECT * FROM images")
    suspend fun getImagesUrls(): List<ImageDto>

    @Query("DELETE FROM images WHERE imageUrl = :imageUrl")
    suspend fun deleteImageUrl(imageUrl: String)
}