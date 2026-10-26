package com.duoc.SafeMuni.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.duoc.SafeMuni.model.NuevoReporteModel
import com.duoc.SafeMuni.repository.NuevoReporteRepository

class NuevoReporteViewModel(
    private val nuevoReporte : NuevoReporteRepository
) : ViewModel(){
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

    fun toggleMapaPicker(mostrar: Boolean) {
        uiState = uiState.copy(mostrarMapaPicker = mostrar)
    }

    fun onUbicacionConfirmadaEnMapa(lat: Double, lng: Double) {
        val textoCoordenadas = "Lat: ${"%.4f".format(lat)}, Lng: ${"%.4f".format(lng)}"
        uiState = uiState.copy(
            latitud = lat,
            longitud = lng,
            ubicacion = textoCoordenadas,
            mostrarMapaPicker = false
        )
    }

    fun onDescripcionChanged(nuevaDescripcion: String) {
        uiState = uiState.copy(descripcion = nuevaDescripcion)
    }

    fun prepararUriCamara(): Uri? {
        val uri = nuevoReporte.crearUriFotoTemporal()
        tempUriCamara = uri
        return uri
    }

    fun onFotoTomadaExito(){
        tempUriCamara?.let { uri ->
            uiState = uiState.copy(fotoUri = uri)
        }
    }

    fun enviarReporte() {
        uiState = uiState.copy(mostrarModalExito = true)
    }

    fun cerrarModal() {
        uiState = uiState.copy(mostrarModalExito = false)
    }

}