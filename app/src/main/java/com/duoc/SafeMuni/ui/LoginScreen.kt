package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.SafeMuni.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginExitoso: () -> Unit,
    onAbrirRegistro: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier.size(90.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF0F4C81)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "🛡️", fontSize = 42.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Iniciar sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Usa credenciales de prueba para ingresar al prototipo.",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Usuario", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = viewModel.usuarioInput,
            onValueChange = {
                viewModel.usuarioInput = it
                viewModel.limpiarMensajes()
            },
            placeholder = { Text("ingresa tu usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Contraseña", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = viewModel.passwordInput,
            onValueChange = {
                viewModel.passwordInput = it
                viewModel.limpiarMensajes()
            },
            placeholder = { Text("••••••••") },
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

        Button(
            onClick = { viewModel.iniciarSesion(onLoginExitoso) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("INGRESAR", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("¿No tienes cuenta? ", fontSize = 13.sp, color = Color.Gray)
            Text(
                text = "¡Regístrate!",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A7BF6),
                modifier = Modifier.clickable { onAbrirRegistro() }
            )
        }

        if (viewModel.mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = Color(0xFFFFEBEE),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = viewModel.mensajeError,
                    color = Color(0xFFC62828),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}