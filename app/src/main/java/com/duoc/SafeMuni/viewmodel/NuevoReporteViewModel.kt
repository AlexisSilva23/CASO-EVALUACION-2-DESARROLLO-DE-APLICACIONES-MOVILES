package com.duoc.SafeMuni.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.SafeMuni.model.NuevoReporteModel
import com.duoc.SafeMuni.repository.NuevoReporteRepository

class NuevoReporteViewModel : ViewModel(){
    private val repository = NuevoReporteRepository()

    var nuevoReporte : NuevoReporteModel by mutableStateOf(repository.getNuevoReporte())
}