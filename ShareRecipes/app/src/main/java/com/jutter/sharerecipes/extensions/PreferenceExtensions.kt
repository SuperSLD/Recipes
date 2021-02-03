package com.jutter.sharerecipes.extensions

import android.content.Context
import pro.midev.iprofi.common.PreferenceHelper

const val SHARE_PREF = "share_pref"

const val IS_AUTH = "is_auth"
const val TOKEN = "token"
const val IS_DAY_THEME = "is_day_theme"


fun Context.saveAuthState(isAuth: Boolean) {
    if (isAuth) {
        PreferenceHelper.customPrefs(this, SHARE_PREF).edit().putBoolean(IS_AUTH, isAuth).apply()
    } else {
        PreferenceHelper.customPrefs(this, SHARE_PREF).edit().clear().apply()
    }
}

fun Context.getAuthState(): Boolean {
    return PreferenceHelper.customPrefs(this, SHARE_PREF).getBoolean(IS_AUTH, false)
}

fun Context.saveToken(token: String) {
    PreferenceHelper.customPrefs(this, SHARE_PREF).edit().putString(TOKEN, token).apply()
}

fun Context.getToken(): String {
    return PreferenceHelper.customPrefs(this, SHARE_PREF).getString(TOKEN, "")!!
}

fun Context.saveIsDayTheme(day: Boolean) {
    PreferenceHelper.customPrefs(this, SHARE_PREF).edit().putBoolean(IS_DAY_THEME, day).apply()
}

fun Context.getIsDayTheme(): Boolean {
    return PreferenceHelper.customPrefs(this, SHARE_PREF).getBoolean(IS_DAY_THEME, true)
}

