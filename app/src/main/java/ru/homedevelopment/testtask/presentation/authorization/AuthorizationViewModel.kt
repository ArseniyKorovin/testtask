package ru.homedevelopment.testtask.presentation.authorization

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.homedevelopment.testtask.App
import ru.homedevelopment.testtask.data.entity.OneTimeDataWrapper
import ru.homedevelopment.testtask.data.repo.PhoneRepo
import ru.homedevelopment.testtask.utils.CORRECT_CODE
import ru.homedevelopment.testtask.utils.CORRECT_NUMBER_PHONE
import javax.inject.Inject

class AuthorizationViewModel : ViewModel() {

    @Inject
    lateinit var phoneRepo: PhoneRepo

    private var phoneNumber: String? = null

    private val mError = MutableStateFlow<OneTimeDataWrapper<String?>>(
        OneTimeDataWrapper(
            null
        )
    )
    val error = mError.asStateFlow()

    private val mCode = MutableStateFlow<Int?>(null)
    val code = mCode.asStateFlow()

    init {
        App.appComponent.inject(this)
    }

    fun setPhone(phone: String) {
        phoneNumber = phone
    }

    fun getCode(errorMessage: String) {
        val phone = phoneNumber
        if (phone.isNullOrEmpty()) {
            mError.value = OneTimeDataWrapper(
                errorMessage
            )
            return
        }
        if (phone.length < 12 || phone != CORRECT_NUMBER_PHONE) {
            mError.value = OneTimeDataWrapper(
                errorMessage
            )
            return
        }
        mCode.value = CORRECT_CODE
    }

    fun checkCode(code: Int, errorMessage: String): Boolean {
        return if (this.code.value == code) {
            true
        } else {
            mError.value = OneTimeDataWrapper(
                errorMessage
            )
            false
        }
    }

    fun isAuthorized(): Boolean {
        return phoneRepo.getPhoneNumber() == CORRECT_NUMBER_PHONE
    }

    fun authorize() {
        phoneNumber?.let {
            phoneRepo.setPhone(it)
        }
    }

    fun clearCodeAndNumber() {
        mCode.value = null
        phoneNumber = null
    }
}