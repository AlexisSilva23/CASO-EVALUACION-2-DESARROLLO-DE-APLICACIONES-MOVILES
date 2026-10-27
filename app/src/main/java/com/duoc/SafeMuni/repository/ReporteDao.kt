package com.duoc.SafeMuni.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duoc.SafeMuni.model.Reporte

@Dao
interface ReporteDao {
    @Query("SELECT * FROM reportes ORDER BY id DESC")
    suspend fun obtenerTodos(): List<Reporte>

    @Insert
    suspend fun insertar(reporte: Reporte): Long
}