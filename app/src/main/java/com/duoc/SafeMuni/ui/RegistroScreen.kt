package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.duoc.SafeMuni.model.Usuario
import com.duoc.SafeMuni.viewmodel.AuthViewModel

@Composable
fun RegistroScreen(
    authViewModel: AuthViewModel,
    onRegistroSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val errorMensaje by authViewModel.mensajeError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Registro de Usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Crea una cuenta para reportar incidencias",
                fontSize = 14.sp,
                color = Color(0xFF64748B),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            CampoRegistro(label = "Nombre", value = nombre, placeholder = "Ingresa tu nombre") { nombre = it; authViewModel.limpiarError() }
            CampoRegistro(label = "Apellido", value = apellido, placeholder = "Ingresa tu apellido") { apellido = it; authViewModel.limpiarError() }
            CampoRegistro(label = "Usuario", value = usuario, placeholder = "Nombre de usuario") { usuario = it; authViewModel.limpiarError() }
            CampoRegistro(label = "Correo Electrónico", value = correo, placeholder = "correo@ejemplo.com", keyboardType = KeyboardType.Email) { correo = it; authViewModel.limpiarError() }
            CampoRegistro(label = "Contraseña", value = password, placeholder = "••••••••", isPassword = true) { password = it; authViewModel.limpiarError() }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && usuario.isNotBlank() && password.isNotBlank()) {
                        val nuevoUsuario = Usuario(
                            nombre = nombre,
                            apellido = apellido,
                            usuario = usuario,
                            correo = correo,
                            password = password
                        )
                        authViewModel.registrar(nuevoUsuario, onSuccess = onRegistroSuccess)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("REGISTRARSE", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
            }

            if (errorMensaje != null) {
                Surface(
                    color = Color(0xFFFEE2E2),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Error de registro", fontWeight = FontWeight.Bold, color = Color(0xFF991B1B), fontSize = 13.sp)
                        Text(errorMensaje ?: "", color = Color(0xFFB91C1C), fontSize = 12.sp)
                    }
                }
            }

            TextButton(onClick = onNavigateToLogin) {
                Text(
                    "¿Ya tienes cuenta? Inicia sesión",
                    color = Color(0xFF2A7BF6),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun CampoRegistro(
    label: String,
    value: String,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
        Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color(0xFF94A3B8)) },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF2A7BF6),
                unfocusedBorderColor = Color(0xFFCBD5E1),
                focusedTextColor = Color(0xFF1E293B),
                unfocusedTextColor = Color(0xFF1E293B)
            ),
            singleLine = true
        )
    }
}