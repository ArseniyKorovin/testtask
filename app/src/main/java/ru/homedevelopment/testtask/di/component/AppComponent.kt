package ru.homedevelopment.testtask.di.component

import dagger.Component
import ru.homedevelopment.testtask.di.module.StorageModule
import ru.homedevelopment.testtask.presentation.authorization.AuthorizationViewModel
import ru.homedevelopment.testtask.presentation.image.ImageViewModel
import ru.homedevelopment.testtask.presentation.main.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        StorageModule::class
    ]
)
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(authorizationViewModel: AuthorizationViewModel)
    fun inject(imageViewModel: ImageViewModel)
}