package com.duoc.SafeMuni.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duoc.SafeMuni.model.Reporte
import kotlinx.coroutines.flow.Flow

@Dao
interface ReporteDao {
    @Query("SELECT * FROM reportes ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<Reporte>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(reporte: Reporte): Long
}