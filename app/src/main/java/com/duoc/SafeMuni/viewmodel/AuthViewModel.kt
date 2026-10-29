package com.duoc.SafeMuni.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.SafeMuni.model.Usuario
import com.duoc.SafeMuni.repository.SessionManager
import com.duoc.SafeMuni.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val usuarioRepository: UsuarioRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    init {
        comprobarSesionGuardada()
    }

    private fun comprobarSesionGuardada() {
        val usuarioGuardado = sessionManager.obtenerUsuarioGuardado()
        if (usuarioGuardado != null) {
            _usuarioLogueado.value = usuarioGuardado
        }
    }

    fun login(user: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioRepository.validarUsuario(user, pass)
            if (usuario != null) {
                _usuarioLogueado.value = usuario
                sessionManager.guardarSesion(usuario)
                _mensajeError.value = null
                onSuccess()
            } else {
                _mensajeError.value = "Usuario o contraseña incorrectos"
            }
        }
    }

    fun registrar(usuario: Usuario, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val existente = usuarioRepository.buscarPorUsuarioOCorreo(usuario.usuario, usuario.correo)
            if (existente != null) {
                _mensajeError.value = "El usuario o correo ya está registrado"
            } else {
                val id = usuarioRepository.registrarUsuario(usuario)
                if (id > 0) {
                    val usuarioConId = usuario.copy(id = id.toInt())
                    _usuarioLogueado.value = usuarioConId
                    sessionManager.guardarSesion(usuarioConId)
                    _mensajeError.value = null
                    onSuccess()
                } else {
                    _mensajeError.value = "Error al registrar usuario"
                }
            }
        }
    }

    fun logout() {
        sessionManager.cerrarSesion()
        _usuarioLogueado.value = null
    }

    fun limpiarError() {
        _mensajeError.value = null
    }
}