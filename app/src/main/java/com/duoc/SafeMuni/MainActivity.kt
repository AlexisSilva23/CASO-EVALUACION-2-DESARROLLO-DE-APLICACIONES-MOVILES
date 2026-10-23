package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.duoc.SafeMuni.ui.InicioScreen
import com.duoc.SafeMuni.ui.MisReportesScreen
// Si tu compañero ya creó NuevoReporteScreen, descomenta la siguiente línea:
// import com.duoc.SafeMuni.ui.NuevoReporteScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado global para controlar la pantalla y la pestaña activa de la barra inferior
            var pantallaActual by remember { mutableStateOf("Inicio") }

            when (pantallaActual) {
                "Inicio" -> {
                    InicioScreen(
                        onNavegarA = { destino -> pantallaActual = destino },
                        onRegistrarIncidenciaClick = {
                            // Redirige directamente a la pantalla de "Nuevo reporte"
                            pantallaActual = "Reportar"
                        }
                    )
                }
                "Reportar" -> {
                    // Cuando tu compañero suba su archivo NuevoReporteScreen.kt, desmarca el comentario de abajo.
                    // NuevoReporteScreen(onNavegarA = { destino -> pantallaActual = destino })

                    // Vista temporal mientras tu compañero sube la pantalla de "Nuevo reporte":
                    VistaTemporalReportar(onNavegarA = { destino -> pantallaActual = destino })
                }
                "Mis casos" -> {
                    MisReportesScreen(
                        onNavegarA = { destino -> pantallaActual = destino },
                        onVerDetalleCaso = { folio ->
                            // Acción para ver el detalle del caso
                        }
                    )
                }
                "Perfil" -> {
                    VistaTemporalPerfil(onNavegarA = { destino -> pantallaActual = destino })
                }
            }
        }
    }
}

@Composable
fun VistaTemporalReportar(onNavegarA: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Pantalla Nuevo Reporte (Integración en proceso)")
    }
}

@Composable
fun VistaTemporalPerfil(onNavegarA: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Pantalla Perfil")
    }
}