package com.duoc.SafeMuni.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.SafeMuni.model.NuevoReporteModel
import com.duoc.SafeMuni.model.Reporte
import com.duoc.SafeMuni.repository.NuevoReporteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NuevoReporteViewModel(
    private val repository: NuevoReporteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NuevoReporteModel())
    val uiState: StateFlow<NuevoReporteModel> = _uiState.asStateFlow()

    fun onCategoriaChanged(nuevaCategoria: String) {
        _uiState.update { it.copy(categoriaReporte = nuevaCategoria) }
    }

    fun onUbicacionChanged(nuevaUbicacion: String) {
        _uiState.update { it.copy(ubicacion = nuevaUbicacion) }
    }

    fun onDescripcionChanged(nuevaDescripcion: String) {
        _uiState.update { it.copy(descripcion = nuevaDescripcion) }
    }

    fun onFotoSelected(uri: Uri?) {
        _uiState.update { it.copy(fotoUri = uri) }
    }

    fun registrarReporte() {
        val estadoActual = _uiState.value
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val folioGenerado = "SM-${System.currentTimeMillis().toString().takeLast(4)}"

        val nuevoReporte = Reporte(
            folio = folioGenerado,
            titulo = estadoActual.categoriaReporte.ifBlank { "Reporte general" },
            categoria = estadoActual.categoriaReporte,
            ubicacion = estadoActual.ubicacion,
            descripcion = estadoActual.descripcion,
            fecha = fechaActual,
            estado = "En revisión",
            fotoUri = estadoActual.fotoUri?.toString()
        )

        viewModelScope.launch {
            repository.guardarReporte(nuevoReporte)
            _uiState.update { it.copy(mostrarModalExito = true) }
        }
    }

    fun cerrarModalExito() {
        _uiState.update {
            NuevoReporteModel()
        }
    }
}