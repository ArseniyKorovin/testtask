package ru.homedevelopment.testtask.di.module

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.homedevelopment.testtask.data.dataBase.DataBase
import ru.homedevelopment.testtask.data.dataBase.ImageDao
import javax.inject.Singleton

@Module
class StorageModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesImageDatabase(): DataBase {
        return Room.databaseBuilder(
            application,
            DataBase::class.java,
            "ImageDataBase"
        ).build()
    }

    @Provides
    @Singleton
    fun providesImageDao(database: DataBase): ImageDao {
        return database.getImageDao()
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}