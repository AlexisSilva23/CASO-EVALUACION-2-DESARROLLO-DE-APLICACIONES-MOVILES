package com.duoc.SafeMuni.repository

import android.content.Context
import com.duoc.SafeMuni.model.Reporte
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NuevoReporteRepository(
    private val context: Context,
    private val reporteDao: ReporteDao
) {
    fun obtenerReportes(): Flow<List<Reporte>> = reporteDao.obtenerTodos()

    suspend fun guardarReporte(reporte: Reporte): Long = withContext(Dispatchers.IO) {
        reporteDao.insertar(reporte)
    }
}