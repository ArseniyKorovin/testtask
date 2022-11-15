package ru.homedevelopment.testtask.presentation.image

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.homedevelopment.testtask.App
import ru.homedevelopment.testtask.data.repo.ImageRepo
import javax.inject.Inject

class ImageViewModel( val imageUrl: String): ViewModel() {

    @Inject
    lateinit var imageRepo: ImageRepo

    init {
        App.appComponent.inject(this)
    }

    fun deleteImageUrl() = viewModelScope.launch(Dispatchers.IO) {
        imageRepo.deleteImageUrl(imageUrl)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val imageUrl: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImageViewModel(imageUrl) as T
        }
    }
}