package com.duoc.SafeMuni.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoReporte(
    viewModel: NuevoReporteViewModel,
    onNavegarA: (String) -> Unit
) {
    val state = viewModel.uiState
    var dropdownExpandido by remember { mutableStateOf(false) }

    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) viewModel.onFotoTomadaExito()
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            val uri = viewModel.prepararUriCamara()
            uri?.let { takePictureLauncher.launch(it) }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F6F8))) {
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF0F4C81)).padding(20.dp)) {
            Column {
                Text("Nuevo reporte", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Completa los antecedentes básicos", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Categoría", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            ExposedDropdownMenuBox(expanded = dropdownExpandido, onExpandedChange = { dropdownExpandido = !dropdownExpandido }) {
                OutlinedTextField(
                    value = state.categoriaReporte,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Seleccionar categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpandido) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                ExposedDropdownMenu(expanded = dropdownExpandido, onDismissRequest = { dropdownExpandido = false }) {
                    state.categoriasDisponibles.forEach { opcion ->
                        DropdownMenuItem(text = { Text(opcion) }, onClick = {
                            viewModel.onCategoriaSelected(opcion)
                            dropdownExpandido = false
                        })
                    }
                }
            }

            Text("Ubicación referencial", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = state.ubicacion,
                onValueChange = { viewModel.onUbicacionManualChanged(it) },
                placeholder = { Text("Seleccionar sector") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Text("Descripción", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = state.descripcion,
                onValueChange = { viewModel.onDescripcionChanged(it) },
                placeholder = { Text("Descripcion del incidente o accion ocurrida") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Text("Evidencia", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Surface(
                modifier = Modifier.fillMaxWidth().height(120.dp).clickable {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                if (state.fotoUri != null) {
                    AsyncImage(model = state.fotoUri, contentDescription = "Foto", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.Add, contentDescription = "Foto", tint = Color(0xFF2A7BF6))
                        Text("Agregar foto", color = Color(0xFF2A7BF6), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Surface(color = Color(0xFFFFF8E7), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Importante", fontWeight = FontWeight.Bold, color = Color(0xFF713F12))
                    Text("No utilizar datos personales reales ni direcciones sensibles.", fontSize = 12.sp, color = Color(0xFF713F12))
                }
            }

            Button(
                onClick = { viewModel.enviarReporte() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Enviar reporte", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        BarraNavegacionInferiorInicio(itemSeleccionado = "Reportar", onNavegarA = onNavegarA)
    }

    if (state.mostrarModalExito) {
        AlertDialog(
            onDismissRequest = { viewModel.cerrarModal() },
            title = { Text("Reporte Enviado") },
            text = { Text("El reporte ha sido registrado exitosamente.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.cerrarModal()
                    onNavegarA("Mis casos")
                }) {
                    Text("Aceptar")
                }
            }
        )
    }
}