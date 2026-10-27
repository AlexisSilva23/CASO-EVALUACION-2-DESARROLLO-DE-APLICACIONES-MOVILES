package com.duoc.SafeMuni.repository

import com.duoc.SafeMuni.model.Usuario

class UsuarioRepository(private val usuarioDao: UsuarioDao) {

    suspend fun registrar(usuario: Usuario): Boolean {
        val existente = usuarioDao.buscarPorUsuarioOCorreo(usuario.usuario, usuario.correo)
        if (existente != null) return false
        return usuarioDao.registrarUsuario(usuario) > 0
    }

    suspend fun validar(usuario: String, password: String): Boolean {
        return usuarioDao.validarUsuario(usuario.trim().lowercase(), password) != null
    }
}