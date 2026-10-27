package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import kotlin.getValue // Import del delegado lazy estándar
import com.duoc.SafeMuni.model.AppDatabase // <-- Corrección de la ubicación real del package
import com.duoc.SafeMuni.repository.NuevoReporteRepository
import com.duoc.SafeMuni.repository.UsuarioRepository
import com.duoc.SafeMuni.ui.*
import com.duoc.SafeMuni.ui.theme.SafeMuniTheme
import com.duoc.SafeMuni.viewmodel.AuthViewModel
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel

class MainActivity : ComponentActivity() {

    // Instancias de Base de Datos y Repositorios
    private val database by lazy { AppDatabase.getDatabase(this) }
    private val usuarioRepo by lazy { UsuarioRepository(database.usuarioDao()) }
    private val reporteRepo by lazy { NuevoReporteRepository(this, database.reporteDao()) }

    // ViewModels creados con 'by lazy'
    private val authViewModel by lazy { AuthViewModel(usuarioRepo) }
    private val reporteViewModel by lazy { NuevoReporteViewModel(reporteRepo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SafeMuniTheme {
                var pantallaActual by remember { mutableStateOf("Login") }
                var casoSeleccionadoId by remember { mutableStateOf("SM-001") }

                when (pantallaActual) {
                    "Login" -> {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginExitoso = { pantallaActual = "Inicio" },
                            onAbrirRegistro = { pantallaActual = "Registro" }
                        )
                    }
                    "Registro" -> {
                        RegistroScreen(
                            viewModel = authViewModel,
                            onVolverLogin = { pantallaActual = "Login" }
                        )
                    }
                    "Inicio" -> {
                        InicioScreen(
                            onNavegarA = { pantallaActual = it },
                            onRegistrarIncidenciaClick = { pantallaActual = "Reportar" },
                            onVerDetalleCaso = { folio ->
                                casoSeleccionadoId = folio
                                pantallaActual = "DetalleCaso"
                            }
                        )
                    }
                    "Reportar" -> {
                        NuevoReporte(
                            viewModel = reporteViewModel,
                            onNavegarA = { pantallaActual = it }
                        )
                    }
                    "Mis casos" -> {
                        MisReportesScreen(
                            onNavegarA = { pantallaActual = it },
                            onVerDetalleCaso = { folio ->
                                casoSeleccionadoId = folio
                                pantallaActual = "DetalleCaso"
                            }
                        )
                    }
                    "DetalleCaso" -> {
                        DetalleCasoScreen(
                            folioId = casoSeleccionadoId,
                            onVolver = { pantallaActual = "Mis casos" },
                            onNavegarA = { pantallaActual = it }
                        )
                    }
                    "Perfil" -> {
                        PerfilScreen(onNavegarA = { pantallaActual = it })
                    }
                }
            }
        }
    }
}