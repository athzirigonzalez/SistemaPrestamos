package com.example.sistemaprestamos



import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun isUserActive(): Boolean {
        return prefs.getBoolean("IS_ACTIVE", true) // Regla de negocio real
    }

    fun setUserActive(active: Boolean) {
        prefs.edit().putBoolean("IS_ACTIVE", active).apply()
    }
}