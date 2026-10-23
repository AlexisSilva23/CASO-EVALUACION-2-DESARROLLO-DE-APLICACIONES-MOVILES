package com.duoc.SafeMuni.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class NuevoReporteModel {
    var categoriaReporte by mutableStateOf("")
    var ubicacionReferencial by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var evidencia by mutableStateOf("")
}