package com.duoc.SafeMuni.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.SafeMuni.R
import com.duoc.SafeMuni.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Logo oficial desde res/drawable/logo_safemuni.png
            Image(
                painter = painterResource(id = R.drawable.logo_safemuni),
                contentDescription = "SafeMuni Logo",
                modifier = Modifier
                    .size(110.dp)
                    .padding(bottom = 4.dp)
            )

            // Títulos
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Usa credenciales de prueba para ingresar al prototipo.",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B)
                )
            }

            // Campos de texto con fondo blanco
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Usuario", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                OutlinedTextField(
                    value = usuario,
                    onValueChange = {
                        usuario = it
                        authViewModel.limpiarError()
                    },
                    placeholder = { Text("ingresa tu usuario", color = Color(0xFF94A3B8)) },
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

                Text("Contraseña", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1E293B))
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        authViewModel.limpiarError()
                    },
                    placeholder = { Text("••••••••", color = Color(0xFF94A3B8)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

            Spacer(modifier = Modifier.height(4.dp))

            // Botón Ingresar
            Button(
                onClick = {
                    if (usuario.isNotBlank() && password.isNotBlank()) {
                        authViewModel.login(usuario, password, onSuccess = onLoginSuccess)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("INGRESAR", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
            }

            // Alerta de Error
            if (errorMensaje != null) {
                Surface(
                    color = Color(0xFFFEE2E2),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Estado de error", fontWeight = FontWeight.Bold, color = Color(0xFF991B1B), fontSize = 13.sp)
                        Text(errorMensaje ?: "", color = Color(0xFFB91C1C), fontSize = 12.sp)
                    }
                }
            }

            TextButton(onClick = onNavigateToRegister) {
                Text(
                    "¿No tienes cuenta? Regístrate aquí",
                    color = Color(0xFF2A7BF6),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Text(
                "Roles de prueba: usuario · Operador · Supervisor",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
            )
        }
    }
}