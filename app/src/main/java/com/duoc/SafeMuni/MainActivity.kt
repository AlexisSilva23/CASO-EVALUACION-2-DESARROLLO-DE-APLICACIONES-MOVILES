package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.duoc.SafeMuni.ui.BarraNavegacionInferiorInicio
import com.duoc.SafeMuni.ui.InicioScreen
import com.duoc.SafeMuni.ui.MisReportesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado global para gestionar la pantalla activa y el indicador azul de la barra inferior
            var pantallaActual by remember { mutableStateOf("Inicio") }

            when (pantallaActual) {
                "Inicio" -> {
                    InicioScreen(
                        onNavegarA = { destino -> pantallaActual = destino },
                        onRegistrarIncidenciaClick = { pantallaActual = "Reportar" }
                    )
                }
                "Reportar" -> {
                    // Vista contenedora temporal con la barra inferior activa en "Reportar"
                    // cuando tu compañero integre NuevoReporteScreen, solo reemplazas el centro de la pantalla
                    PantallaContenedoraReportar(onNavegarA = { destino -> pantallaActual = destino })
                }
                "Mis casos" -> {
                    MisReportesScreen(
                        onNavegarA = { destino -> pantallaActual = destino },
                        onVerDetalleCaso = { folio ->
                            // Acción para abrir el detalle del caso
                        }
                    )
                }
                "Perfil" -> {
                    PantallaContenedoraPerfil(onNavegarA = { destino -> pantallaActual = destino })
                }
            }
        }
    }
}

@Composable
fun PantallaContenedoraReportar(onNavegarA: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pantalla 'Nuevo reporte' (En desarrollo por compañero)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Reportar",
            onNavegarA = onNavegarA
        )
    }
}

@Composable
fun PantallaContenedoraPerfil(onNavegarA: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Pantalla Perfil",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
        BarraNavegacionInferiorInicio(
            itemSeleccionado = "Perfil",
            onNavegarA = onNavegarA
        )
    }
}