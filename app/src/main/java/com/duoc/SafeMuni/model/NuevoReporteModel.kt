package com.duoc.SafeMuni.model

import android.net.Uri

data class NuevoReporteModel (
    val categoriaReporte: String = "",
    val categoriasDisponibles : List<String> = listOf(
        "Alumbrado Público",
        "Basura / Limpieza",
        "Seguridad / Incidencias",
        "Vía Pública / Baches",
        "Áreas Verdes",
        "Otros"
    ),
    val ubicacion : String = "",
    val latitud: Double? = null,
    val longitud: Double? = null,
    val descripcion : String = "",
    val fotoUri : Uri? = null,
    val mostrarModalExito: Boolean = false,
    val mostrarMapaPicker: Boolean = false
    )