package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.SafeMuni.ui.BarraNavegacionInferiorInicio
import com.duoc.SafeMuni.ui.InicioScreen
import com.duoc.SafeMuni.ui.MisReportesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia del helper de base de datos para SQLite
        val dbHelper = DatabaseHelper(this)

        setContent {
            // Control de navegación principal (Inicia en "Login")
            var pantallaActual by remember { mutableStateOf("Login") }

            // Guarda el ID o Folio del caso seleccionado para ver su detalle
            var casoSeleccionadoId by remember { mutableStateOf("SM-001") }

            when (pantallaActual) {
                "Login" -> {
                    PantallaLoginReal(
                        dbHelper = dbHelper,
                        onLoginExitoso = { pantallaActual = "Inicio" }
                    )
                }
                "Inicio" -> {
                    InicioScreen(
                        onNavegarA = { destino -> pantallaActual = destino },
                        onRegistrarIncidenciaClick = { pantallaActual = "Reportar" },
                        onVerDetalleCaso = { folio ->
                            casoSeleccionadoId = folio
                            pantallaActual = "DetalleCaso"
                        }
                    )
                }
                "Reportar" -> {
                    PantallaContenedoraReportar(onNavegarA = { destino -> pantallaActual = destino })
                }
                "Mis casos" -> {
                    MisReportesScreen(
                        onNavegarA = { destino -> pantallaActual = destino },
                        onVerDetalleCaso = { folio ->
                            casoSeleccionadoId = folio
                            pantallaActual = "DetalleCaso"
                        }
                    )
                }
                "DetalleCaso" -> {
                    PantallaContenedoraDetalleCaso(
                        folioId = casoSeleccionadoId,
                        onVolver = { pantallaActual = "Mis casos" },
                        onNavegarA = { destino -> pantallaActual = destino }
                    )
                }
                "Perfil" -> {
                    PantallaContenedoraPerfil(onNavegarA = { destino -> pantallaActual = destino })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaLoginReal(
    dbHelper: DatabaseHelper,
    onLoginExitoso: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mensajeEstado by remember { mutableStateOf("") }
    var esError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo / Escudo Ilustrativo
            Surface(
                modifier = Modifier.size(90.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F4C81)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "🛡️",
                        fontSize = 42.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Título
            Text(
                text = "Iniciar sesión",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Ingresa tus credenciales para acceder a SafeMuni",
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Campo Usuario
            Text(
                text = "Usuario",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = usuario,
                onValueChange = {
                    usuario = it
                    mensajeEstado = ""
                },
                placeholder = { Text("Ej: vecino123") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Contraseña
            Text(
                text = "Contraseña",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    mensajeEstado = ""
                },
                placeholder = { Text("••••••") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Ingresar
            Button(
                onClick = {
                    if (usuario.isBlank() || password.isBlank()) {
                        mensajeEstado = "Por favor completa todos los campos"
                        esError = true
                    } else {
                        val esValido = dbHelper.validarUsuario(usuario, password)
                        if (esValido || usuario.lowercase() == "demo") { // Permite 'demo' para pruebas
                            mensajeEstado = "Inicio de sesión correcto."
                            esError = false
                            onLoginExitoso()
                        } else {
                            mensajeEstado = "Usuario o contraseña incorrectos"
                            esError = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "INGRESAR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Registro rápido
            Row {
                Text(text = "¿No tienes cuenta? ", fontSize = 13.sp, color = Color.Gray)
                Text(
                    text = "¡Regístrate!",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2A7BF6),
                    modifier = Modifier.clickable {
                        // Registra un usuario de prueba rápido si la BD está vacía
                        dbHelper.registrarUsuario("Vecino", "Prueba", "vecino@duoc.cl", "aaaa", "123456")
                        usuario = "aaaa"
                        password = "123456"
                        mensajeEstado = "Usuario 'aaaa' registrado. Presiona INGRESAR"
                        esError = false
                    }
                )
            }

            // Cartel de alerta/estado
            if (mensajeEstado.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = if (esError) Color(0xFFFFEBEE) else Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mensajeEstado,
                        color = if (esError) Color(0xFFC62828) else Color(0xFF2E7D32),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "SafeMuni · Seguridad Comunal", fontSize = 11.sp, color = Color.Gray)
        }
    }
}

// Vista contenedora del Detalle del Caso
@Composable
fun PantallaContenedoraDetalleCaso(
    folioId: String,
    onVolver: () -> Unit,
    onNavegarA: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // Encabezado Azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F4C81))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Detalle del caso",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Folio sintético: $folioId",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Iluminación deficiente",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Surface(
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "En revisión",
                        color = Color(0xFFE65100),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Categoría
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Categoría", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Alumbrado / entorno", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Ubicación
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Ubicación", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Sector referencial", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Descripción
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Descripción", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("poca iluminacion en san joaquin por la noche.", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Volver
            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Volver a mis reportes", color = Color(0xFF0F4C81), fontWeight = FontWeight.Bold)
            }
        }

        // Barra Inferior activa en "Mis casos"
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Mis casos",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
fun PantallaContenedoraReportar(onNavegarA: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pantalla 'Nuevo reporte' (En desarrollo por compañero)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Reportar",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
fun PantallaContenedoraPerfil(onNavegarA: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pantalla Perfil",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Perfil",
            onNavegarA = onNavegarA
        )
    }
}