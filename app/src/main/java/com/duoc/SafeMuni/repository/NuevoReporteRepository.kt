package com.duoc.SafeMuni.repository

import com.duoc.SafeMuni.model.NuevoReporteModel

class NuevoReporteRepository {
    private var nuevoReporte = NuevoReporteModel()

    fun getNuevoReporte(): NuevoReporteModel = nuevoReporte
}