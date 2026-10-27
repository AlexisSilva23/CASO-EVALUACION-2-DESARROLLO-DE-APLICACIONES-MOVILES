package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetalleCasoScreen(
    folioId: String,
    onVolver: () -> Unit,
    onNavegarA: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F6F8))) {
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFF0F4C81)).padding(20.dp)) {
            Column {
                Text("Detalle del caso", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Folio sintético: $folioId", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }

        Column(modifier = Modifier.weight(1f).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Iluminación deficiente", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Surface(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(12.dp)) {
                    Text("En revisión", color = Color(0xFFE65100), fontSize = 12.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Categoría", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Alumbrado / entorno", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Ubicación", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("Sector referencial", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Descripción", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Text("poca iluminacion en san joaquin por la noche.", color = Color.Gray, fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = onVolver, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                Text("Volver a mis reportes", color = Color(0xFF0F4C81), fontWeight = FontWeight.Bold)
            }
        }

        BarraNavegacionInferiorInicio(itemSeleccionado = "Mis casos", onNavegarA = onNavegarA)
    }
}