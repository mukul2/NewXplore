package com.rafiq.newxplore.utlis

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


/**
 * Created by hrsoftbd_mk_1 on 2/5/2018.
 */


/**
 * Created by mkl on 2/8/2018.
 */
class SessionManager(cntx: Context?) {
    private val prefs: SharedPreferences

    var token: String?
        get() = prefs.getString("token", "")
        set(nid) {
            prefs.edit().putString("token", nid).commit()
        }

    var loggedIn: Boolean
        get() = prefs.getBoolean("loggedIn", false)
        set(nid) {
            prefs.edit().putBoolean("loggedIn", nid).commit()
        }

    var isSuperAdmin: Boolean
        get() = prefs.getBoolean("superAdmin", false)
        set(nid) {
            prefs.edit().putBoolean("superAdmin", nid).commit()
        }

    fun setuserType(nid: String?) {
        prefs.edit().putString("userType", nid).commit()
    }

    val userType: String?
        get() = prefs.getString("userType", "")

    fun setuserName(nid: String?) {
        prefs.edit().putString("userName", nid).commit()
    }

    val userName: String?
        get() = prefs.getString("userName", "")

    fun setuserId(nid: String?) {
        prefs.edit().putString("id", nid).commit()
    }

    val userId: String?
        get() = prefs.getString("id", "")

    fun setSound(nid: Boolean) {
        prefs.edit().putBoolean("sound", nid).commit()
    }

    fun getsound(): Boolean {
        return prefs.getBoolean("sound", true)
    }

    fun setnewCaseNoti(nid: Boolean) {
        prefs.edit().putBoolean("newCaseNoti", nid).commit()
    }

    fun getnewCaseNoti(): Boolean {
        return prefs.getBoolean("newCaseNoti", true)
    }

    var newMsgNoti: Boolean
        get() = prefs.getBoolean("newMsgNoti", true)
        set(nid) {
            prefs.edit().putBoolean("newMsgNoti", nid).commit()
        }

    fun set_lang(usename: String?) {
        prefs.edit().putString("lang", usename).commit()
    }

    fun get_lang(): String? {
        return prefs.getString("lang", "bn")
    }

    fun set_userPhoto(usename: String?) {
        prefs.edit().putString("userPhoto", usename).commit()
    }

    fun get_userPhoto(): String? {
        return prefs.getString("userPhoto", "")
    }

    fun set_userMobile(usename: String?) {
        prefs.edit().putString("mobile", usename).commit()
    }

    fun get_userMobile(): String? {
        return prefs.getString("mobile", "")
    }

    fun set_userdisplay(usename: String?) {
        prefs.edit().putString("display", usename).commit()
    }

    fun get_userdisplay(): String? {
        return prefs.getString("display", "")
    }

    fun set_userEmail(usename: String?) {
        prefs.edit().putString("email", usename).commit()
    }

    fun get_userEmail(): String? {
        return prefs.getString("email", "")
    }

    fun set_userPass(usename: String?) {
        prefs.edit().putString("password", usename).commit()
    }

    fun get_userPass(): String? {
        return prefs.getString("password", "")
    }

    fun set_isCallEnabled(usename: Boolean) {
        prefs.edit().putBoolean("isCallEnabled", usename).commit()
    }

    fun get_isCallEnabled(): Boolean {
        return prefs.getBoolean("isCallEnabled", false)
    }

    fun set_userStatus(usename: Boolean) {
        prefs.edit().putBoolean("status", usename).commit()
    }

    fun get_userStatus(): Boolean {
        return prefs.getBoolean("status", false)
    }

    init { // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }
}