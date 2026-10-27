package com.duoc.SafeMuni.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reportes")
data class Reporte(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val folio: String,
    val titulo: String,
    val categoria: String,
    val ubicacion: String,
    val descripcion: String,
    val fecha: String,
    val estado: String = "En revisión"
)

data class NuevoReporteModel(
    val categoriaReporte: String = "",
    val categoriasDisponibles: List<String> = listOf(
        "Alumbrado / entorno",
        "Basura / Limpieza",
        "Seguridad / Incidencias",
        "Vía Pública / Baches",
        "Áreas Verdes",
        "Otros"
    ),
    val ubicacion: String = "",
    val descripcion: String = "",
    val fotoUri: Uri? = null,
    val mostrarModalExito: Boolean = false
)