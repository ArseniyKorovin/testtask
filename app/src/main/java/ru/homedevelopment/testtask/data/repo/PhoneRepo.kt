package ru.homedevelopment.testtask.data.repo

import ru.homedevelopment.testtask.data.prefsstorage.PrefsStorage
import javax.inject.Inject

class PhoneRepo @Inject constructor(
    private val prefsStorage: PrefsStorage
) {
    fun getPhoneNumber() : String = prefsStorage.getPhoneSaveNumber() ?: ""

    fun deletePhone() = prefsStorage.deletePhoneNumber()

    fun setPhone(phoneNumber: String) {
        prefsStorage.setPhoneSaveNumber(phoneNumber)
    }
}