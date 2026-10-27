package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    onRegistrarIncidenciaClick: () -> Unit = { onNavegarA("Reportar") },
    onVerDetalleCaso: (String) -> Unit = {}
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F4C81))
                .padding(20.dp)
        ) {
            Column {
                Text("Hola, Vecino", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Rol: Persona", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F1FF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("¿Qué ocurrió?", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F4C81))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Registra una situación de seguridad comunal.", fontSize = 13.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = onRegistrarIncidenciaClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Registrar incidencia", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Column {
                    Text("Mis reportes", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ContadorCard("2", "En revisión", Color(0xFFFFF3E0), Color(0xFFE65100), Modifier.weight(1f))
                        ContadorCard("1", "En proceso", Color(0xFFE3F2FD), Color(0xFF1565C0), Modifier.weight(1f))
                        ContadorCard("3", "Cerrados", Color(0xFFE8F5E9), Color(0xFF2E7D32), Modifier.weight(1f))
                    }
                }
            }

            item {
                Text("Casos recientes", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            }

            items(casosRecientes) { caso ->
                TarjetaCasoInicioItem(caso = caso, onClick = { onVerDetalleCaso(caso.id) })
            }
        }

        BarraNavegacionInferiorInicio(itemSeleccionado = "Inicio", onNavegarA = onNavegarA)
    }
}

@Composable
fun ContadorCard(cantidad: String, titulo: String, colorFondo: Color, colorTexto: Color, modifier: Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = colorFondo), shape = RoundedCornerShape(12.dp), modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(cantidad, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = colorTexto)
            Text(titulo, fontSize = 11.sp, color = colorTexto)
        }
    }
}

@Composable
fun TarjetaCasoInicioItem(caso: CasoInicioUI, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(caso.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF1E293B))
            Text("${caso.sector} · ${caso.fecha}", fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = if (caso.estado == "En revisión") Color(0xFFFFF3E0) else Color(0xFFE3F2FD),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(caso.estado, color = if (caso.estado == "En revisión") Color(0xFFE65100) else Color(0xFF1565C0), fontSize = 11.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
            }
        }
    }
}

@Composable
fun BarraNavegacionInferiorInicio(itemSeleccionado: String, onNavegarA: (String) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), color = Color.White, shadowElevation = 8.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            ItemNavegacionInicio("Inicio", itemSeleccionado == "Inicio") { onNavegarA("Inicio") }
            ItemNavegacionInicio("Reportar", itemSeleccionado == "Reportar") { onNavegarA("Reportar") }
            ItemNavegacionInicio("Mis casos", itemSeleccionado == "Mis casos") { onNavegarA("Mis casos") }
            ItemNavegacionInicio("Perfil", itemSeleccionado == "Perfil") { onNavegarA("Perfil") }
        }
    }
}

@Composable
fun ItemNavegacionInicio(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val colorCirculo = if (isSelected) Color(0xFF2A7BF6) else Color(0xFFD0D5DD)
    val colorTexto = if (isSelected) Color(0xFF2A7BF6) else Color(0xFF667085)
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }.padding(horizontal = 12.dp, vertical = 4.dp)) {
        Box(modifier = Modifier.size(20.dp).background(color = colorCirculo, shape = CircleShape))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = colorTexto)
    }
}