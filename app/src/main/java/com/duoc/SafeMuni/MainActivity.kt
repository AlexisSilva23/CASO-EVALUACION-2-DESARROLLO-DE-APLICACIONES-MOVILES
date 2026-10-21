package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.duoc.SafeMuni.ui.InicioScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InicioScreen(
                onNavegarA = { destino ->
                    // Callback listo para la integración con tu grupo
                },
                onRegistrarIncidenciaClick = {
                    // Acción para el botón "Registrar incidencia"
                }
            )
        }
    }
}