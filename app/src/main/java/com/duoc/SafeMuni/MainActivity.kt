package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room

import com.duoc.SafeMuni.repository.AppDatabase
import com.duoc.SafeMuni.repository.NuevoReporteRepository
import com.duoc.SafeMuni.ui.InicioScreen
import com.duoc.SafeMuni.ui.LoginScreen
import com.duoc.SafeMuni.ui.MapaPickerDialog
import com.duoc.SafeMuni.ui.MisReportesScreen
import com.duoc.SafeMuni.ui.NuevoReporte
import com.duoc.SafeMuni.ui.PerfilScreen
import com.duoc.SafeMuni.ui.theme.SafeMuniTheme
import com.duoc.SafeMuni.viewmodel.AuthViewModel
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel

class NuevoReporteViewModelFactory(
    private val repository: NuevoReporteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NuevoReporteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NuevoReporteViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel no reconocido")
    }
}

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "safemuni.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = NuevoReporteRepository(applicationContext, db.reporteDao())
        val viewModelFactory = NuevoReporteViewModelFactory(repository)

        setContent {
            SafeMuniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val nuevoReporteViewModel: NuevoReporteViewModel = viewModel(factory = viewModelFactory)

                    var pantallaActual by remember { mutableStateOf("nuevo_reporte") }
                    var mostrarMapaDialog by remember { mutableStateOf(false) }
                    val usuarioCorreo by remember { mutableStateOf("usuario@safemuni.cl") }

                    when (pantallaActual) {
                        "inicio", "Inicio" -> {
                            InicioScreen(onNavegarA = { pantallaActual = it })
                        }

                        "nuevo_reporte", "Reportar" -> {
                            NuevoReporte(
                                viewModel = nuevoReporteViewModel,
                                usuarioCorreo = usuarioCorreo,
                                onNavegarA = { destino ->
                                    pantallaActual = destino
                                },
                                onAbrirMapa = {
                                    mostrarMapaDialog = true
                                }
                            )
                        }

                        "mis_reportes", "Mis casos" -> {
                            MisReportesScreen(onNavegarA = { pantallaActual = it })
                        }

                        "perfil", "Perfil" -> {
                            PerfilScreen(onNavegarA = { pantallaActual = it })
                        }

                        "login" -> {
                            val authViewModel: AuthViewModel = viewModel()
                            LoginScreen(
                                authViewModel = authViewModel,
                                onLoginSuccess = { pantallaActual = "inicio" },
                                onNavigateToRegister = { pantallaActual = "registro" }
                            )
                        }

                        else -> {
                            NuevoReporte(
                                viewModel = nuevoReporteViewModel,
                                usuarioCorreo = usuarioCorreo,
                                onNavegarA = { destino -> pantallaActual = destino },
                                onAbrirMapa = { mostrarMapaDialog = true }
                            )
                        }
                    }

                    // Dialog Modal del Mapa
                    if (mostrarMapaDialog) {
                        MapaPickerDialog(
                            latInicial = -33.4489,
                            lngInicial = -70.6693,
                            onDismiss = { mostrarMapaDialog = false },
                            onConfirmar = { lat, lng ->
                                nuevoReporteViewModel.onUbicacionChanged("Lat: $lat, Lng: $lng")
                                mostrarMapaDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}