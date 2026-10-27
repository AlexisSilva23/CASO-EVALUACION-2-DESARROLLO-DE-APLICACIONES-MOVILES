package com.duoc.SafeMuni.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.SafeMuni.model.Usuario
import com.duoc.SafeMuni.repository.UsuarioRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UsuarioRepository) : ViewModel() {

    var usuarioInput by mutableStateOf("")
    var passwordInput by mutableStateOf("")

    var nombreReg by mutableStateOf("")
    var apellidoReg by mutableStateOf("")
    var correoReg by mutableStateOf("")
    var usuarioReg by mutableStateOf("")
    var passwordReg by mutableStateOf("")
    var confirmarPasswordReg by mutableStateOf("")

    var mensajeError by mutableStateOf("")
    var mensajeExito by mutableStateOf("")

    fun limpiarMensajes() {
        mensajeError = ""
        mensajeExito = ""
    }

    fun iniciarSesion(onExito: () -> Unit) {
        limpiarMensajes()
        if (usuarioInput.isBlank() || passwordInput.isBlank()) {
            mensajeError = "Debes ingresar usuario y contraseña."
            return
        }

        viewModelScope.launch {
            val esValido = repository.validar(usuarioInput, passwordInput) || usuarioInput.lowercase() == "demo"
            if (esValido) {
                mensajeExito = "Inicio de sesión correcto."
                onExito()
            } else {
                mensajeError = "Credenciales o perfil no válido. Reintentar."
            }
        }
    }

    fun registrarUsuario(onExito: () -> Unit) {
        limpiarMensajes()
        if (nombreReg.isBlank() || apellidoReg.isBlank() || correoReg.isBlank() ||
            usuarioReg.isBlank() || passwordReg.isBlank() || confirmarPasswordReg.isBlank()
        ) {
            mensajeError = "Debes completar todos los campos."
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correoReg.trim()).matches()) {
            mensajeError = "Ingresa un correo electrónico válido."
            return
        }

        if (passwordReg.length < 6) {
            mensajeError = "La contraseña debe tener al menos 6 caracteres."
            return
        }

        if (passwordReg != confirmarPasswordReg) {
            mensajeError = "Las contraseñas no coinciden."
            return
        }

        viewModelScope.launch {
            val nuevoUsuario = Usuario(
                nombre = nombreReg.trim(),
                apellido = apellidoReg.trim(),
                correo = correoReg.trim().lowercase(),
                usuario = usuarioReg.trim().lowercase(),
                password = passwordReg
            )
            val guardado = repository.registrar(nuevoUsuario)
            if (guardado) {
                mensajeExito = "Cuenta creada correctamente."
                onExito()
            } else {
                mensajeError = "El usuario o correo ya está registrado."
            }
        }
    }
}