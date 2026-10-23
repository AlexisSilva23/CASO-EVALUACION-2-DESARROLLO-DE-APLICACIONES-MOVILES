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

data class CasoInicioUI(
    val id: String,
    val titulo: String,
    val sector: String,
    val fecha: String,
    val estado: String
)

@Composable
fun InicioScreen(
    onNavegarA: (String) -> Unit = {},
    onRegistrarIncidenciaClick: () -> Unit = { onNavegarA("Reportar") }
) {
    val casosRecientes = remember {
        listOf(
            CasoInicioUI("SM-001", "Poca iluminacion", "Sector referencial", "Hace 2 h", "En revisión"),
            CasoInicioUI("SM-002", "Vehículo abandonado", "Sector referencial", "Ayer", "En proceso")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // Encabezado Azul Superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F4C81))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Hola, Vecino",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Rol: Persona",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        // Cuerpo Principal Desplazable
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta ¿Qué ocurrió?
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F1FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "¿Qué ocurrió?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F4C81)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Registra una situación de seguridad comunal.",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = onRegistrarIncidenciaClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Registrar incidencia",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Sección Contadores
            item {
                Column {
                    Text(
                        text = "Mis reportes",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ContadorCard(cantidad = "2", titulo = "En revisión", colorFondo = Color(0xFFFFF3E0), colorTexto = Color(0xFFE65100), modifier = Modifier.weight(1f))
                        ContadorCard(cantidad = "1", titulo = "En proceso", colorFondo = Color(0xFFE3F2FD), colorTexto = Color(0xFF1565C0), modifier = Modifier.weight(1f))
                        ContadorCard(cantidad = "3", titulo = "Cerrados", colorFondo = Color(0xFFE8F5E9), colorTexto = Color(0xFF2E7D32), modifier = Modifier.weight(1f))
                    }
                }
            }

            // Casos Recientes
            item {
                Text(
                    text = "Casos recientes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            items(casosRecientes) { caso ->
                TarjetaCasoInicioItem(caso = caso)
            }
        }

        // Barra de Navegación Inferior
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Inicio",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
fun ContadorCard(
    cantidad: String,
    titulo: String,
    colorFondo: Color,
    colorTexto: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = cantidad, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = colorTexto)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = titulo, fontSize = 11.sp, color = colorTexto)
        }
    }
}

@Composable
fun TarjetaCasoInicioItem(caso: CasoInicioUI) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
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
fun BarraNavegacionInferiorInicio(
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
            ItemNavegacionInicio(label = "Inicio", isSelected = itemSeleccionado == "Inicio", onClick = { onNavegarA("Inicio") })
            ItemNavegacionInicio(label = "Reportar", isSelected = itemSeleccionado == "Reportar", onClick = { onNavegarA("Reportar") })
            ItemNavegacionInicio(label = "Mis casos", isSelected = itemSeleccionado == "Mis casos", onClick = { onNavegarA("Mis casos") })
            ItemNavegacionInicio(label = "Perfil", isSelected = itemSeleccionado == "Perfil", onClick = { onNavegarA("Perfil") })
        }
    }
}

@Composable
fun ItemNavegacionInicio(
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