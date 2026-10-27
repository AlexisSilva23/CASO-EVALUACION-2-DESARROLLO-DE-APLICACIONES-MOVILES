package com.duoc.SafeMuni.repository

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.duoc.SafeMuni.model.Reporte
import java.io.File

class NuevoReporteRepository(
    private val context: Context,
    private val reporteDao: ReporteDao
) {
    fun crearUriFotoTemporal(): Uri? {
        return try {
            val file = File.createTempFile("evidencia_", ".jpg", context.cacheDir)
            file.deleteOnExit()
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun guardarReporte(reporte: Reporte) {
        reporteDao.insertar(reporte)
    }

    suspend fun obtenerReportes(): List<Reporte> {
        return reporteDao.obtenerTodos()
    }
}