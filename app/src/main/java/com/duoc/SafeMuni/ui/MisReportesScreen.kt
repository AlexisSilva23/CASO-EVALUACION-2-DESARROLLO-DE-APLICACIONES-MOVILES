package com.duoc.SafeMuni.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CasoReporteUI(
    val id: String,
    val titulo: String,
    val sector: String,
    val fecha: String,
    val estado: String
)

@Composable
fun MisReportesScreen(
    onNavegarA: (String) -> Unit = {},
    onVerDetalleCaso: (String) -> Unit = {}
) {
    var busqueda by remember { mutableStateOf("") }
    var filtroSeleccionado by remember { mutableStateOf("Todos") }

    val listaCasosPrueba = remember {
        listOf(
            CasoReporteUI("SM-001", "Iluminación deficiente", "Sector referencial", "hoy", "En revisión"),
            CasoReporteUI("SM-002", "Vehículo abandonado", "Sector referencial", "Ayer, 18:10", "En proceso"),
            CasoReporteUI("SM-003", "Ruido nocturno", "Sector referencial", "10 oct", "Cerrado")
        )
    }

    val casosFiltrados = listaCasosPrueba.filter { caso ->
        val coincideTexto = caso.titulo.contains(busqueda, ignoreCase = true) || caso.sector.contains(busqueda, ignoreCase = true)
        val coincideFiltro = when (filtroSeleccionado) {
            "En revisión" -> caso.estado == "En revisión"
            "Cerrados" -> caso.estado == "Cerrado"
            else -> true
        }
        coincideTexto && coincideFiltro
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F6F8))) {
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF0F4C81)).padding(20.dp)) {
            Column {
                Text("Mis reportes", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Seguimiento de incidencias creadas", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }

        Column(modifier = Modifier.weight(1f).padding(16.dp)) {
            Text("Buscar", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar por título") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChipItem("Todos", filtroSeleccionado == "Todos") { filtroSeleccionado = "Todos" }
                FilterChipItem("En revisión", filtroSeleccionado == "En revisión") { filtroSeleccionado = "En revisión" }
                FilterChipItem("Cerrados", filtroSeleccionado == "Cerrados") { filtroSeleccionado = "Cerrados" }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AnimatedVisibility(
                visible = casosFiltrados.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(casosFiltrados) { caso ->
                        TarjetaReporteItem(caso = caso, onClick = { onVerDetalleCaso(caso.id) })
                    }
                }
            }

            if (casosFiltrados.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("No se encontraron reportes", color = Color.Gray)
                }
            }
        }

        BarraNavegacionInferiorInicio(itemSeleccionado = "Mis casos", onNavegarA = onNavegarA)
    }
}

@Composable
fun FilterChipItem(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        color = if (isSelected) Color(0xFFE8F1FF) else Color(0xFFF1F5F9),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            color = if (isSelected) Color(0xFF2A7BF6) else Color(0xFF64748B),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun TarjetaReporteItem(caso: CasoReporteUI, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(caso.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))
            Text("${caso.sector} · ${caso.fecha}", fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                color = if (caso.estado == "En revisión") Color(0xFFFFF3E0) else Color(0xFFE8F5E9),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(caso.estado, color = if (caso.estado == "En revisión") Color(0xFFE65100) else Color(0xFF2E7D32), fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
    }
}