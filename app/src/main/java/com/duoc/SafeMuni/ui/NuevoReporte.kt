package com.duoc.SafeMuni.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel

@Composable
fun NuevoReporte(viewModel: NuevoReporteViewModel) {

    var abrirModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Text("Categoria")
        OutlinedTextField(
            value = viewModel.nuevoReporte.categoriaReporte,
            onValueChange = {viewModel.nuevoReporte.categoriaReporte = it},
            label = {Text("Seleccionar Categoria")}
        )

        //Text("Ubicacion Referencial")


        //Text("Descripcion")
        OutlinedTextField(
            value = viewModel.nuevoReporte.descripcion,
            onValueChange = {viewModel.nuevoReporte.descripcion = it},
            label = {Text("Descripcion del incidente o accion")}
        )

        //Text("Evidencia")


        //Button() {
        //    Text("Enviar Reporte")
        //}
        if (abrirModal) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Confirmación") },
                text = { Text("Formulario enviado correctamente") },
                confirmButton = {
                    Button(onClick = { abrirModal = false }) { Text("OK") }
                }
            )
        }

    }
}