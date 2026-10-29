package com.duoc.SafeMuni.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.io.File

@Composable
fun DetalleCasoScreen(
    folioId: String = "SM-001",
    listaReportes: List<CasoReporteUI> = emptyList(),
    onVolver: () -> Unit,
    onNavegarA: (String) -> Unit
) {
    val listaCasosPrueba = listOf(
        CasoReporteUI("SM-001", "Iluminación deficiente", "Sector referencial", "Hoy", "En revisión", "Alumbrado / entorno", "poca iluminacion en san joaquin por la noche."),
        CasoReporteUI("SM-002", "Vehículo abandonado", "Sector referencial", "Ayer, 18:10", "En proceso", "Vehículos / Tránsito", "Vehículo estacionado por más de 3 días sin placa."),
        CasoReporteUI("SM-003", "Ruido nocturno", "Sector referencial", "10 oct", "Cerrado", "Fiscalización", "Música con volumen alto en la vía pública.")
    )

    val caso = (listaReportes + listaCasosPrueba).find { it.id == folioId }
        ?: CasoReporteUI(folioId, "Reporte no encontrado", "Sector referencial", "Hoy", "En revisión", "General", "Sin descripción")

    // Determina la fuente adecuada para Coil (archivo físico local o URI)
    val fotoModelo: Any? = remember(caso.fotoUri) {
        caso.fotoUri?.let { path ->
            if (path.startsWith("/")) File(path) else path
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        // Encabezado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0F4C81))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Detalle del caso",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Folio: ${caso.id}",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        // Contenido scrollable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = caso.titulo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    modifier = Modifier.weight(1f)
                )

                val (colorFondo, colorTexto) = when (caso.estado) {
                    "En revisión" -> Color(0xFFFEF3C7) to Color(0xFFD97706)
                    "En proceso" -> Color(0xFFE0F2FE) to Color(0xFF0284C7)
                    else -> Color(0xFFDCFCE7) to Color(0xFF15803D)
                }

                Surface(
                    color = colorFondo,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = caso.estado,
                        color = colorTexto,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            TarjetaInformacion(
                titulo = "Categoría",
                contenido = caso.categoria.ifBlank { "Alumbrado / entorno" }
            )

            TarjetaInformacion(
                titulo = "Ubicación",
                contenido = caso.sector
            )

            TarjetaInformacion(
                titulo = "Descripción",
                contenido = caso.descripcion.ifBlank { "Sin descripción detallada registrada." }
            )

            // Renderizado de la Evidencia Fotográfica
            if (fotoModelo != null) {
                Text(
                    text = "Evidencia fotográfica",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF1E293B)
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    color = Color.White
                ) {
                    AsyncImage(
                        model = fotoModelo,
                        contentDescription = "Evidencia fotográfica",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Historial de Seguimiento
            Text(
                text = "Seguimiento",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.padding(top = 4.dp)
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    when (caso.estado) {
                        "En revisión" -> {
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "Reporte creado", subtitulo = "${caso.fecha} · 12:30")
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "Recibido por municipio", subtitulo = "${caso.fecha} · 12:42")
                            ItemSeguimiento(dotColor = Color(0xFFD97706), titulo = "En revisión", subtitulo = "Pendiente de derivación")
                        }
                        "En proceso" -> {
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "Reporte creado", subtitulo = caso.fecha)
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "Recibido por municipio", subtitulo = "Validado")
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "En revisión", subtitulo = "Aprobado por operador")
                            ItemSeguimiento(dotColor = Color(0xFF0284C7), titulo = "En proceso", subtitulo = "Cuadrilla asignada a terreno")
                        }
                        else -> {
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "Reporte creado", subtitulo = caso.fecha)
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "Recibido por municipio", subtitulo = "Procesado")
                            ItemSeguimiento(dotColor = Color(0xFF2A7BF6), titulo = "En proceso", subtitulo = "Trabajos finalizados")
                            ItemSeguimiento(dotColor = Color(0xFF15803D), titulo = "Cerrado", subtitulo = "Incidencia resuelta exitosamente")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFF0F4C81))
            ) {
                Text(
                    text = "Volver a mis reportes",
                    color = Color(0xFF0F4C81),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Mis casos",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
private fun TarjetaInformacion(titulo: String, contenido: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF1E293B)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = contenido,
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
private fun ItemSeguimiento(dotColor: Color, titulo: String, subtitulo: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(dotColor, shape = CircleShape)
        )
        Column {
            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF1E293B)
            )
            Text(
                text = subtitulo,
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )
        }
    }
}