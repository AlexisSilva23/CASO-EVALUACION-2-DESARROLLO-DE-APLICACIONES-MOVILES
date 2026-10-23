package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
        val coincideTexto = caso.titulo.contains(busqueda, ignoreCase = true) ||
                caso.sector.contains(busqueda, ignoreCase = true)
        val coincideFiltro = when (filtroSeleccionado) {
            "En revisión" -> caso.estado == "En revisión"
            "Cerrados" -> caso.estado == "Cerrado"
            else -> true
        }
        coincideTexto && coincideFiltro
    }

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
                    text = "Mis reportes",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Seguimiento de incidencias creadas",
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
            // Buscador
            Text(text = "Buscar", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar por título") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chips de Filtro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChipItem(label = "Todos", isSelected = filtroSeleccionado == "Todos", onClick = { filtroSeleccionado = "Todos" })
                FilterChipItem(label = "En revisión", isSelected = filtroSeleccionado == "En revisión", onClick = { filtroSeleccionado = "En revisión" })
                FilterChipItem(label = "Cerrados", isSelected = filtroSeleccionado == "Cerrados", onClick = { filtroSeleccionado = "Cerrados" })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Listado de Casos
            if (casosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No se encontraron reportes", color = Color.Gray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(casosFiltrados) { caso ->
                        TarjetaReporteItem(
                            caso = caso,
                            onClick = { onVerDetalleCaso(caso.id) }
                        )
                    }
                }
            }
        }

        // Barra inferior
        BarraNavegacionInferiorReportes(
            itemSeleccionado = "Mis casos",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFE8F1FF) else Color(0xFFF1F5F9)
    val textColor = if (isSelected) Color(0xFF2A7BF6) else Color(0xFF64748B)

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun TarjetaReporteItem(
    caso: CasoReporteUI,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = caso.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "${caso.sector} · ${caso.fecha}", fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))

            val (badgeBg, badgeText) = when (caso.estado) {
                "En revisión" -> Color(0xFFFFF3E0) to Color(0xFFE65100)
                "En proceso" -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
                else -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
            }

            Surface(color = badgeBg, shape = RoundedCornerShape(12.dp)) {
                Text(
                    text = caso.estado,
                    color = badgeText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun BarraNavegacionInferiorReportes(
    itemSeleccionado: String,
    onNavegarA: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ItemNavegacionReportes(label = "Inicio", isSelected = itemSeleccionado == "Inicio", onClick = { onNavegarA("Inicio") })
            ItemNavegacionReportes(label = "Reportar", isSelected = itemSeleccionado == "Reportar", onClick = { onNavegarA("Reportar") })
            ItemNavegacionReportes(label = "Mis casos", isSelected = itemSeleccionado == "Mis casos", onClick = { onNavegarA("Mis casos") })
            ItemNavegacionReportes(label = "Perfil", isSelected = itemSeleccionado == "Perfil", onClick = { onNavegarA("Perfil") })
        }
    }
}

@Composable
fun ItemNavegacionReportes(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colorCirculo = if (isSelected) Color(0xFF2A7BF6) else Color(0xFFD0D5DD)
    val colorTexto = if (isSelected) Color(0xFF2A7BF6) else Color(0xFF667085)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color = colorCirculo, shape = CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = colorTexto
        )
    }
}

