package com.duoc.SafeMuni.repository

import com.duoc.SafeMuni.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun validarUsuario(user: String, pass: String): Usuario? = withContext(Dispatchers.IO) {
        usuarioDao.validarUsuario(user, pass)
    }

    suspend fun registrarUsuario(usuario: Usuario): Long = withContext(Dispatchers.IO) {
        usuarioDao.registrarUsuario(usuario)
    }

    suspend fun buscarPorUsuarioOCorreo(user: String, correo: String): Usuario? = withContext(Dispatchers.IO) {
        usuarioDao.buscarPorUsuarioOCorreo(user, correo)
    }
}