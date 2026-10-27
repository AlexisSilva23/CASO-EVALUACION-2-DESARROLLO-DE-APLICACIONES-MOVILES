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
fun RegistroScreen(
    viewModel: AuthViewModel,
    onVolverLogin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear cuenta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B), modifier = Modifier.fillMaxWidth())
        Text("Completa tus datos para registrarte en SafeMuni.", fontSize = 13.sp, color = Color.Gray, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = viewModel.nombreReg, onValueChange = { viewModel.nombreReg = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.apellidoReg, onValueChange = { viewModel.apellidoReg = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.correoReg, onValueChange = { viewModel.correoReg = it }, label = { Text("Correo electrónico") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.usuarioReg, onValueChange = { viewModel.usuarioReg = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.passwordReg, onValueChange = { viewModel.passwordReg = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = viewModel.confirmarPasswordReg, onValueChange = { viewModel.confirmarPasswordReg = it }, label = { Text("Confirmar contraseña") }, visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { viewModel.registrarUsuario(onVolverLogin) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("CREAR CUENTA", fontWeight = FontWeight.Bold, color = Color.White)
        }

        if (viewModel.mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(color = Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                Text(text = viewModel.mensajeError, color = Color(0xFFC62828), fontSize = 13.sp, modifier = Modifier.padding(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("¿Ya tienes cuenta? Iniciar sesión", color = Color(0xFF2A7BF6), fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onVolverLogin() })
    }
}

