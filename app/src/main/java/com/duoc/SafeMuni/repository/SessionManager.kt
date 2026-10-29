package com.duoc.SafeMuni.repository

import android.content.Context
import android.content.SharedPreferences
import com.duoc.SafeMuni.model.Usuario

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("safemuni_session", Context.MODE_PRIVATE)

    fun guardarSesion(usuario: Usuario) {
        prefs.edit().apply {
            putInt("user_id", usuario.id)
            putString("user_nombre", usuario.nombre)
            putString("user_apellido", usuario.apellido)
            putString("user_usuario", usuario.usuario)
            putString("user_correo", usuario.correo)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    fun obtenerUsuarioGuardado(): Usuario? {
        val isLoggedIn = prefs.getBoolean("is_logged_in", false)
        if (!isLoggedIn) return null

        return Usuario(
            id = prefs.getInt("user_id", 0),
            nombre = prefs.getString("user_nombre", "") ?: "",
            apellido = prefs.getString("user_apellido", "") ?: "",
            usuario = prefs.getString("user_usuario", "") ?: "",
            correo = prefs.getString("user_correo", "") ?: "",
            password = ""
        )
    }

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }
}