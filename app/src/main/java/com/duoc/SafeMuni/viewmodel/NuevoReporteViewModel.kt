package com.duoc.SafeMuni.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.SafeMuni.model.NuevoReporteModel
import com.duoc.SafeMuni.model.Reporte
import com.duoc.SafeMuni.repository.NuevoReporteRepository
import kotlinx.coroutines.launch

class NuevoReporteViewModel(
    private val repository: NuevoReporteRepository
) : ViewModel() {

    var uiState by mutableStateOf(NuevoReporteModel())
        private set

    var tempUriCamara: Uri? = null
        private set

    fun onCategoriaSelected(nuevaCategoria: String) {
        uiState = uiState.copy(categoriaReporte = nuevaCategoria)
    }

    fun onUbicacionManualChanged(nuevaUbicacion: String) {
        uiState = uiState.copy(ubicacion = nuevaUbicacion)
    }

    fun onDescripcionChanged(nuevaDescripcion: String) {
        uiState = uiState.copy(descripcion = nuevaDescripcion)
    }

    fun prepararUriCamara(): Uri? {
        val uri = repository.crearUriFotoTemporal()
        tempUriCamara = uri
        return uri
    }

    fun onFotoTomadaExito() {
        tempUriCamara?.let { uri ->
            uiState = uiState.copy(fotoUri = uri)
        }
    }

    fun enviarReporte() {
        if (uiState.categoriaReporte.isBlank() || uiState.descripcion.isBlank()) return

        viewModelScope.launch {
            val nuevoReporte = Reporte(
                folio = "SM-00${(10..99).random()}",
                titulo = uiState.categoriaReporte,
                categoria = uiState.categoriaReporte,
                ubicacion = if (uiState.ubicacion.isBlank()) "Sector referencial" else uiState.ubicacion,
                descripcion = uiState.descripcion,
                fecha = "Hoy"
            )
            repository.guardarReporte(nuevoReporte)
            uiState = uiState.copy(mostrarModalExito = true)
        }
    }

    fun cerrarModal() {
        uiState = NuevoReporteModel()
    }
}