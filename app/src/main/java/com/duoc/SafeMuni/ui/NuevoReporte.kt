package com.duoc.SafeMuni.ui

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoReporte(
    viewModel: NuevoReporteViewModel,
    usuarioCorreo: String = "",
    onNavegarA: (String) -> Unit = {},
    onAbrirMapa: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }
    var expandirCategorias by remember { mutableStateOf(false) }

    // Launcher para tomar foto con la cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            viewModel.onFotoSelected(tempCameraUri)
        }
    }

    fun tomarFotoConCamara() {
        try {
            val file = File(context.cacheDir, "camera_photo_${System.currentTimeMillis()}.jpg")
            val uri = FileProvider.getUriForFile(
                context,
                "com.duoc.SafeMuni.provider", // Autoridad correspondiente a AndroidManifest
                file
            )
            tempCameraUri = uri
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al abrir la cámara: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    // Permiso explícito en tiempo de ejecución
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            tomarFotoConCamara()
        } else {
            Toast.makeText(
                context,
                "Debes conceder el permiso de cámara para tomar la evidencia.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun verificarYTomarFoto() {
        val tienePermiso = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (tienePermiso) {
            tomarFotoConCamara()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        // Banner Superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A4B7C))
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Column {
                Text(
                    text = "Nuevo reporte",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Completa los antecedentes básicos",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Categoría
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Categoría",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                ExposedDropdownMenuBox(
                    expanded = expandirCategorias,
                    onExpandedChange = { expandirCategorias = !expandirCategorias }
                ) {
                    OutlinedTextField(
                        value = uiState.categoriaReporte,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Seleccionar categoría", color = Color(0xFF94A3B8)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirCategorias) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color(0xFFCBD5E1),
                            unfocusedBorderColor = Color(0xFFE2E8F0)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expandirCategorias,
                        onDismissRequest = { expandirCategorias = false }
                    ) {
                        uiState.categoriasDisponibles.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    viewModel.onCategoriaChanged(cat)
                                    expandirCategorias = false
                                }
                            )
                        }
                    }
                }
            }

            // 2. Ubicación referencial con Icono de Gotita que activa la API de Mapa
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Ubicación referencial",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                OutlinedTextField(
                    value = uiState.ubicacion,
                    onValueChange = { viewModel.onUbicacionChanged(it) },
                    placeholder = { Text("Seleccionar sector", color = Color(0xFF94A3B8)) },
                    trailingIcon = {
                        IconButton(
                            onClick = { onAbrirMapa() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Abrir mapa",
                                tint = Color(0xFF0A4B7C)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFCBD5E1),
                        unfocusedBorderColor = Color(0xFFE2E8F0)
                    )
                )
            }

            // 3. Descripción
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Descripción",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                OutlinedTextField(
                    value = uiState.descripcion,
                    onValueChange = { viewModel.onDescripcionChanged(it) },
                    placeholder = { Text("Descripcion del incidente o accion ocurrida", color = Color(0xFF94A3B8)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFFCBD5E1),
                        unfocusedBorderColor = Color(0xFFE2E8F0)
                    )
                )
            }

            // 4. Evidencia (+ Agregar foto)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "Evidencia",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clickable { verificarYTomarFoto() }
                ) {
                    if (uiState.fotoUri != null) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = uiState.fotoUri,
                                contentDescription = "Foto capturada",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            IconButton(
                                onClick = { viewModel.onFotoSelected(null) },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(20.dp))
                                    .size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Quitar foto",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color(0xFF2563EB),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Agregar foto",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0A4B7C)
                            )
                        }
                    }
                }
            }

            // 5. Cuadro Importante
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFFFBEB),
                border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Importante",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF92400E)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "No utilizar datos personales reales ni direcciones sensibles en la descripción.",
                        fontSize = 13.sp,
                        color = Color(0xFFB45309)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 6. Botón Enviar reporte
            Button(
                onClick = { viewModel.registrarReporte() },
                enabled = uiState.categoriaReporte.isNotBlank() && uiState.ubicacion.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB),
                    disabledContainerColor = Color(0xFF93C5FD)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Enviar reporte",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        // Modal de Éxito
        if (uiState.mostrarModalExito) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.cerrarModalExito()
                            onNavegarA("mis_reportes")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                    ) {
                        Text("Ver mis reportes", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                },
                title = { Text("¡Reporte enviado!", fontWeight = FontWeight.Bold) },
                text = { Text("Tu reporte ha sido ingresado correctamente y será derivado al área correspondiente.") }
            )
        }

        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Reportar",
            onNavegarA = onNavegarA
        )
    }
}