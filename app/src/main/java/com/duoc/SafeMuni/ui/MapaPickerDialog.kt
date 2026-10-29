package com.duoc.SafeMuni.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapaPickerDialog(
    latInicial: Double,
    lngInicial: Double,
    onDismiss: () -> Unit,
    onConfirmar: (Double, Double) -> Unit
) {
    var marcadorPosicion by remember { mutableStateOf(LatLng(latInicial, lngInicial)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(marcadorPosicion, 15f)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Toca el mapa para fijar la ubicación",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                GoogleMap(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    cameraPositionState = cameraPositionState,
                    onMapClick = { latLng ->
                        marcadorPosicion = latLng
                    }
                ) {
                    Marker(
                        state = MarkerState(position = marcadorPosicion),
                        title = "Ubicación del reporte"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            onConfirmar(marcadorPosicion.latitude, marcadorPosicion.longitude)
                        }
                    ) {
                        Text("Confirmar Ubicación")
                    }
                }
            }
        }
    }
}

