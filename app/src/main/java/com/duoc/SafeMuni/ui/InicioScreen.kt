package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InicioScreen(
    nombreUsuario: String = "Vecino",
    reportesNuevos: List<CasoReporteUI> = emptyList(),
    onNavegarA: (String) -> Unit = {},
    onVerDetalleCaso: (String) -> Unit = {}
) {
    val listaCasosPrueba = remember {
        listOf(
            CasoReporteUI("SM-001", "Poca iluminacion", "Sector referencial", "Hace 2 h", "En revisión"),
            CasoReporteUI("SM-002", "Vehículo abandonado", "Sector referencial", "Ayer", "En proceso")
        )
    }

    val todosLosCasos = remember(reportesNuevos) {
        reportesNuevos + listaCasosPrueba
    }

    val cantEnRevision = todosLosCasos.count { it.estado == "En revisión" }
    val cantEnProceso = todosLosCasos.count { it.estado == "En proceso" }
    val cantCerrados = todosLosCasos.count { it.estado == "Cerrado" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // Banner Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F4C81))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Hola, $nombreUsuario",
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

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tarjeta "¿Qué ocurrió?"
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F1FF)),
                shape = RoundedCornerShape(12.dp),
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
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavegarA("Reportar") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A7BF6)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().height(44.dp)
                    ) {
                        Text("Registrar incidencia", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            // Sección Mis reportes (Contadores)
            Text("Mis reportes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TarjetaContadorState(
                    cantidad = cantEnRevision.toString(),
                    etiqueta = "En revisión",
                    colorFondo = Color(0xFFFFF3E0),
                    colorNumero = Color(0xFFE65100),
                    modifier = Modifier.weight(1f)
                )
                TarjetaContadorState(
                    cantidad = cantEnProceso.toString(),
                    etiqueta = "En proceso",
                    colorFondo = Color(0xFFE0F2FE),
                    colorNumero = Color(0xFF0284C7),
                    modifier = Modifier.weight(1f)
                )
                TarjetaContadorState(
                    cantidad = cantCerrados.toString(),
                    etiqueta = "Cerrados",
                    colorFondo = Color(0xFFE8F5E9),
                    colorNumero = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f)
                )
            }

            // Sección Casos recientes
            Text("Casos recientes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            todosLosCasos.take(3).forEach { caso ->
                TarjetaReporteItem(
                    caso = caso,
                    onClick = { onVerDetalleCaso(caso.id) }
                )
            }
        }

        // Barra inferior de navegación
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Inicio",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
private fun TarjetaContadorState(
    cantidad: String,
    etiqueta: String,
    colorFondo: Color,
    colorNumero: Color,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorFondo),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(cantidad, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = colorNumero)
            Spacer(modifier = Modifier.height(2.dp))
            Text(etiqueta, fontSize = 11.sp, color = Color(0xFF475569), fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun BarraNavegacionInferiorInicio(
    itemSeleccionado: String,
    onNavegarA: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White
    ) {
        val items = listOf(
            Triple("Inicio", Icons.Default.Home, "Inicio"),
            Triple("Reportar", Icons.Default.AddCircle, "Reportar"),
            Triple("Mis casos", Icons.Default.List, "Mis casos"),
            Triple("Perfil", Icons.Default.Person, "Perfil")
        )

        items.forEach { (etiqueta, icono, ruta) ->
            val seleccionado = itemSeleccionado == etiqueta ||
                    (etiqueta == "Reportar" && itemSeleccionado == "nuevo_reporte") ||
                    (etiqueta == "Mis casos" && itemSeleccionado == "mis_reportes")

            NavigationBarItem(
                selected = seleccionado,
                onClick = { onNavegarA(ruta) },
                icon = {
                    Icon(
                        imageVector = icono,
                        contentDescription = etiqueta,
                        tint = if (seleccionado) Color(0xFF2A7BF6) else Color(0xFF94A3B8)
                    )
                },
                label = {
                    Text(
                        text = etiqueta,
                        fontSize = 11.sp,
                        color = if (seleccionado) Color(0xFF2A7BF6) else Color(0xFF94A3B8)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFE8F1FF)
                )
            )
        }
    }
}