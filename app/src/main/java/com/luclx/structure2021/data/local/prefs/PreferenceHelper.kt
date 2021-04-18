package com.luclx.structure2021.data.local.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sin.buildingInsights.utils.encrypt

class PreferenceHelper constructor(private val sharedReference: SharedPreferences) {

    private fun getString(key: String, value: String): String =
        sharedReference.getString(key, value) ?: value

    private fun putString(key: String, value: String) =
        sharedReference.edit { putString(key, value) }

    private fun getBoolean(key: String, value: Boolean): Boolean =
        sharedReference.getBoolean(key, value) ?: value

    private fun putBoolean(key: String, value: Boolean) =
        sharedReference.edit { putBoolean(key, value) }

    private fun getStringEncrypt(key: String, value: String): String =
        sharedReference.getString(encrypt(key), value) ?: value

    private fun putStringEncrypt(key: String, value: String) =
        sharedReference.edit { putString(encrypt(key), encrypt(value)) }

    private fun putInt(key: String, value: Int) = sharedReference.edit { putInt(key, value) }

    private fun getInt(key: String) = sharedReference.getInt(key, 0)

    private fun putLong(key: String, value: Long) = sharedReference.edit { putLong(key, value) }

    private fun getLong(key: String, value: Long) = sharedReference.getLong(key, value)

    var accessToken: String
        get() = getString("access_token", "")
        set(value) = putString("access_token", value)
}
