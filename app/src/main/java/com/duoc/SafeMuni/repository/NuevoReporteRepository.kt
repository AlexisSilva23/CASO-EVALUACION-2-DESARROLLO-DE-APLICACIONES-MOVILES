package com.duoc.SafeMuni.repository

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


class NuevoReporteRepository (private val context: Context) {
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
}
