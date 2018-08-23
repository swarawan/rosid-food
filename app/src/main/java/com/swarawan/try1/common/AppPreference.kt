package com.swarawan.try1.common

import com.swarawan.corelibrary.sharedprefs.CorePreferences
import com.swarawan.corelibrary.utils.TextUtils

/**
 * Created by Rio Swarawn on 8/12/18.
 */
class AppPreference(private val corePreferences: CorePreferences) {

    companion object {
        const val ACCESS_TOKEN = "accessToken"
        const val RAW_TOKEN = "rawToken"
        const val USER_ID = "userId"
        const val NAME = "name"
        const val EMAIL = "email"
        const val PHONE = "phone"
    }

    var accessToken: String
        get() = "Bearer ${corePreferences.getString(ACCESS_TOKEN, TextUtils.BLANK)}"
        set(accessToken) = corePreferences.setString(ACCESS_TOKEN, accessToken)

    var rawToken: String
        get() = corePreferences.getString(RAW_TOKEN, TextUtils.BLANK)
        set(rawToken) = corePreferences.setString(RAW_TOKEN, rawToken)

    var userId: String
        get() = corePreferences.getString(USER_ID, TextUtils.BLANK)
        set(address) = corePreferences.setString(USER_ID, address)

    var name: String
        get() = corePreferences.getString(NAME, TextUtils.BLANK)
        set(address) = corePreferences.setString(NAME, address)

    var email: String
        get() = corePreferences.getString(EMAIL, TextUtils.BLANK)
        set(address) = corePreferences.setString(EMAIL, address)

    var phone: String
        get() = corePreferences.getString(PHONE, TextUtils.BLANK)
        set(address) = corePreferences.setString(PHONE, address)

    fun setCredential(userName: String, userId: String, userEmail: String, userPhone: String, token: String) {
        this.name = userName
        this.userId = userId
        this.email = userEmail
        this.phone = userPhone
        this.accessToken = token
        this.rawToken = token
    }

    fun setToken(token: String) {
        this.accessToken = token
        this.rawToken = token
    }

    fun clear() {
        corePreferences.clear()
    }

    fun isUserLoggedIn(): Boolean = rawToken.isNotEmpty()
}