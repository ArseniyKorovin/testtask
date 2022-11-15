package ru.homedevelopment.testtask

import android.app.Application
import ru.homedevelopment.testtask.di.component.AppComponent
import ru.homedevelopment.testtask.di.component.DaggerAppComponent
import ru.homedevelopment.testtask.di.module.StorageModule

class App: Application() {
    companion object {
        lateinit var instance: App
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        appComponent = DaggerAppComponent.builder()
            .storageModule(StorageModule(this))
            .build()
    }
}