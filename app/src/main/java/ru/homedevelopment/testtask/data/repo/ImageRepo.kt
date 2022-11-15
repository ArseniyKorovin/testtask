package ru.homedevelopment.testtask.data.repo

import ru.homedevelopment.testtask.data.dataBase.ImageDao
import ru.homedevelopment.testtask.data.dataBase.ImageDto
import ru.homedevelopment.testtask.data.prefsstorage.PrefsStorage
import javax.inject.Inject

class ImageRepo @Inject constructor(
    private val imageDao: ImageDao,
    private val prefsStorage: PrefsStorage
    ) {
    suspend fun getImageUrls(): List<String> {
        return imageDao.getImagesUrls().map { it.imageUrl }
    }

    suspend fun deleteImageUrl(imageUrl: String) {
        imageDao.deleteImageUrl(imageUrl)
    }

    suspend fun setImageUrls(imagesUrls: List<String>) {
        imageDao.setImagesUrls(
            imagesUrls.map { ImageDto(it) }
        )
    }

    fun setUpdateDb(needUpdate: Boolean) {
        prefsStorage.setUpdateDb(needUpdate)
    }

    fun getUpdateDb(): Boolean {
        return prefsStorage.getUpdateDb()
    }
}