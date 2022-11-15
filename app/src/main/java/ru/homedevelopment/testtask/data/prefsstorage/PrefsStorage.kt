package ru.homedevelopment.testtask.data.prefsstorage

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val KEY_PHONE_NUMBER = "PHONE_NUMBER"
        private const val KEY_UPDATE_DB = "UPDATE_DB"
    }

    fun getPhoneSaveNumber(): String? {
        return sharedPreferences.getString(KEY_PHONE_NUMBER, "")
    }

    fun setPhoneSaveNumber(phoneNumber: String) {
        sharedPreferences.edit()
            .putString(KEY_PHONE_NUMBER, phoneNumber)
            .apply()
    }

    fun getUpdateDb(): Boolean {
        return sharedPreferences.getBoolean(KEY_UPDATE_DB, true)
    }

    fun setUpdateDb(needUpdate: Boolean) {
        sharedPreferences.edit()
            .putBoolean(KEY_UPDATE_DB, needUpdate)
            .apply()
    }

    fun deletePhoneNumber() {
        sharedPreferences.edit().remove(KEY_PHONE_NUMBER).apply()
    }
}