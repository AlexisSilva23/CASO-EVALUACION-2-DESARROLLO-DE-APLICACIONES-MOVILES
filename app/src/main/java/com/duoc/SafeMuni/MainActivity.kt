package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.duoc.SafeMuni.ui.InicioScreen
import com.duoc.SafeMuni.ui.MisReportesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Estado para controlar qué pantalla está visible
            var pantallaActual by remember { mutableStateOf("Inicio") }

            when (pantallaActual) {
                "Inicio" -> InicioScreen(
                    onNavegarA = { destino ->
                        pantallaActual = destino
                    },
                    onRegistrarIncidenciaClick = {
                        // Aquí se conectará la pantalla del formulario de registro
                    }
                )
                "Mis casos" -> MisReportesScreen(
                    onNavegarA = { destino ->
                        pantallaActual = destino
                    },
                    onVerDetalleCaso = { folio ->
                        // Aquí se conectará la pantalla de detalle
                    }
                )
                else -> {
                    // Pantalla por defecto si no coincide el nombre
                    InicioScreen(
                        onNavegarA = { destino ->
                            pantallaActual = destino
                        }
                    )
                }
            }
        }
    }
}


