package com.duoc.SafeMuni.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.duoc.SafeMuni.repository.NuevoReporteRepository
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel

// --- PALETA DE COLORES DEL BOCETO ---
val BlueHeader = Color(0xFF0B4A72)
val BackgroundGray = Color(0xFFF4F6F8)
val PrimaryBlue = Color(0xFF3B82F6)
val TextDark = Color(0xFF1E293B)
val BorderGray = Color(0xFFE2E8F0)
val WarningBackground = Color(0xFFFFF8E7)
val WarningText = Color(0xFF713F12)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoReporte(
    viewModel: NuevoReporteViewModel = viewModel(
        factory = run {
            val context = LocalContext.current.applicationContext
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NuevoReporteViewModel(NuevoReporteRepository(context)) as T
                }
            }
        }
    )
) {
    val state = viewModel.uiState
    var dropdownExpandido by remember { mutableStateOf(false) }

    // Launchers de Cámara
    val takePictureLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) viewModel.onFotoTomadaExito()
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = viewModel.prepararUriCamara()
            uri?.let { takePictureLauncher.launch(it) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
    ) {
        // --- 1. CABECERA AZUL ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BlueHeader)
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Nuevo reporte",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Completa los antecedentes básicos",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }

        // --- 2. CUERPO DEL FORMULARIO ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // CATEGORÍA
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Categoría",
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                ExposedDropdownMenuBox(
                    expanded = dropdownExpandido,
                    onExpandedChange = { dropdownExpandido = !dropdownExpandido }
                ) {
                    OutlinedTextField(
                        value = state.categoriaReporte,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Seleccionar categoría", color = Color.Gray) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpandido) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = BorderGray
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = dropdownExpandido,
                        onDismissRequest = { dropdownExpandido = false }
                    ) {
                        state.categoriasDisponibles.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    viewModel.onCategoriaSelected(opcion)
                                    dropdownExpandido = false
                                }
                            )
                        }
                    }
                }
            }

            // UBICACIÓN REFERENCIAL + BOTÓN MAPA
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Ubicación referencial",
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = state.ubicacion,
                        onValueChange = { viewModel.onUbicacionManualChanged(it) },
                        placeholder = { Text("Seleccionar sector", color = Color.Gray) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = BorderGray
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { viewModel.toggleMapaPicker(true) },
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .border(1.dp, BorderGray, shape = RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Seleccionar en mapa",
                            tint = PrimaryBlue
                        )
                    }
                }
            }

            // DESCRIPCIÓN
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Descripción",
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                OutlinedTextField(
                    value = state.descripcion,
                    onValueChange = { viewModel.onDescripcionChanged(it) },
                    placeholder = { Text("Descripcion del incidente o accion ocurrida", color = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = PrimaryBlue,
                        unfocusedBorderColor = BorderGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // EVIDENCIA (TARJETA BLANCA / VISTA PREVIA)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Evidencia",
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clickable {
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, BorderGray)
                ) {
                    if (state.fotoUri != null) {
                        Box(contentAlignment = Alignment.Center) {
                            AsyncImage(
                                model = state.fotoUri,
                                contentDescription = "Evidencia",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Surface(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = "Toca para cambiar foto",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Agregar foto",
                                tint = PrimaryBlue,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Agregar foto",
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }

            // BANNER AMARILLO "IMPORTANTE"
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = WarningBackground
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Importante",
                        color = WarningText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "No utilizar datos personales reales ni direcciones sensibles en el reporte.",
                        color = WarningText.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // BOTÓN PRINCIPAL
            Button(
                onClick = { viewModel.enviarReporte() },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text(
                    text = "Enviar reporte",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // --- DIÁLOGOS DE MODAL ---
    if (state.mostrarMapaPicker) {
        MapaPickerDialog(
            latInicial = state.latitud ?: -33.4489,
            lngInicial = state.longitud ?: -70.6693,
            onDismiss = { viewModel.toggleMapaPicker(false) },
            onConfirmar = { lat, lng ->
                viewModel.onUbicacionConfirmadaEnMapa(lat, lng)
            }
        )
    }

    if (state.mostrarModalExito) {
        AlertDialog(
            onDismissRequest = { viewModel.cerrarModal() },
            title = { Text("Reporte Enviado") },
            text = { Text("El reporte ha sido enviado exitosamente.") },
            confirmButton = {
                Button(onClick = { viewModel.cerrarModal() }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

