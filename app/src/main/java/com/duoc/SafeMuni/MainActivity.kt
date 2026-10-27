package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.duoc.SafeMuni.ui.login.InicioSesionScreen
import com.duoc.SafeMuni.ui.registro.RegistroScreen
import com.duoc.SafeMuni.ui.theme.SafeMuniTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            SafeMuniTheme {

                SafeMuniApp()
            }
        }
    }
}

@Composable
fun SafeMuniApp() {

    var pantalla by rememberSaveable {
        mutableStateOf("login")
    }

    when (pantalla) {

        "registro" -> {

            RegistroScreen(
                volverLogin = {
                    pantalla = "login"
                }
            )
        }

        else -> {

            InicioSesionScreen(
                abrirRegistro = {
                    pantalla = "registro"
                }
            )
        }
    }
}