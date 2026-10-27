package com.duoc.SafeMuni.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duoc.SafeMuni.model.Usuario

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE usuario = :user AND password = :pass LIMIT 1")
    suspend fun validarUsuario(user: String, pass: String): Usuario?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registrarUsuario(usuario: Usuario): Long

    @Query("SELECT * FROM usuarios WHERE usuario = :user OR correo = :correo LIMIT 1")
    suspend fun buscarPorUsuarioOCorreo(user: String, correo: String): Usuario?
}