package com.duoc.SafeMuni

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.SafeMuni.ui.theme.SafeMuniTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            SafeMuniTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF4F7FA)
                ) {

                    SafeMuniApp()
                }
            }
        }
    }
}

@Composable
fun SafeMuniApp() {

    var pantallaRegistro by rememberSaveable {
        mutableStateOf(false)
    }

    if (pantallaRegistro) {

        RegistroScreen(
            volverLogin = {
                pantallaRegistro = false
            }
        )

    } else {

        LoginScreen(
            abrirRegistro = {
                pantallaRegistro = true
            }
        )
    }
}

@Composable
fun LogoSafeMuni(
    grande: Boolean = true
) {

    val alturaContenedor =
        if (grande) 175.dp else 110.dp

    val alturaLogo =
        if (grande) 150.dp else 95.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(alturaContenedor),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.logo_safemuni
            ),
            contentDescription = "Logo SafeMuni",
            modifier = Modifier
                .fillMaxWidth()
                .height(alturaLogo)
                .graphicsLayer(
                    scaleX = if (grande) 1.30f else 1f,
                    scaleY = if (grande) 1.30f else 1f
                ),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun LoginScreen(
    abrirRegistro: () -> Unit
) {

    val context = LocalContext.current

    val database = remember {
        DatabaseHelper(context)
    }

    var usuario by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var mensajeError by rememberSaveable {
        mutableStateOf("")
    }

    var mensajeCorrecto by rememberSaveable {
        mutableStateOf("")
    }

    val azul = Color(0xFF2F80ED)
    val fondo = Color(0xFFF4F7FA)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 35.dp,
                bottom = 30.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LogoSafeMuni(
            grande = true
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Iniciar sesión",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF17212B)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Ingresa tus credenciales para acceder a SafeMuni.",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 13.sp,
            color = Color(0xFF7B8491)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Usuario",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF202833)
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        OutlinedTextField(
            value = usuario,
            onValueChange = {

                usuario = it

                mensajeError = ""
                mensajeCorrecto = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {

                Text(
                    text = "Ingresa tu usuario",
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(9.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Contraseña",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF202833)
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {

                password = it

                mensajeError = ""
                mensajeCorrecto = ""
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {

                Text(
                    text = "••••••••",
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            visualTransformation =
                PasswordVisualTransformation(),
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        KeyboardType.Password
                ),
            shape = RoundedCornerShape(9.dp)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {

                if (
                    usuario.isBlank() ||
                    password.isBlank()
                ) {

                    mensajeCorrecto = ""

                    mensajeError =
                        "Debes ingresar usuario y contraseña."

                } else {

                    val loginCorrecto =
                        database.validarUsuario(
                            usuario =
                                usuario.trim(),
                            password =
                                password
                        )

                    if (loginCorrecto) {

                        mensajeError = ""

                        mensajeCorrecto =
                            "Inicio de sesión correcto."

                    } else {

                        mensajeCorrecto = ""

                        mensajeError =
                            "Usuario o contraseña incorrectos."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(8.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = azul
                )
        ) {

            Text(
                text = "INGRESAR",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = buildAnnotatedString {

                append(
                    "¿No tienes cuenta? "
                )

                withStyle(
                    style = SpanStyle(
                        color = azul,
                        fontWeight =
                            FontWeight.Bold
                    )
                ) {

                    append(
                        "¡Regístrate!"
                    )
                }
            },
            fontSize = 14.sp,
            modifier = Modifier.clickable {

                abrirRegistro()
            }
        )

        if (mensajeError.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(9.dp),
                color =
                    Color(0xFFFFE3E3)
            ) {

                Text(
                    text = mensajeError,
                    modifier =
                        Modifier.padding(15.dp),
                    fontSize = 13.sp,
                    color =
                        Color(0xFF8A2424)
                )
            }
        }

        if (mensajeCorrecto.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(9.dp),
                color =
                    Color(0xFFE1F5E5)
            ) {

                Text(
                    text = mensajeCorrecto,
                    modifier =
                        Modifier.padding(15.dp),
                    fontSize = 13.sp,
                    color =
                        Color(0xFF236B35)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        HorizontalDivider(
            color = Color(0xFFE1E5E9)
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text =
                "SafeMuni · Seguridad Comunal",
            fontSize = 11.sp,
            color = Color(0xFF7E8792)
        )
    }
}

@Composable
fun RegistroScreen(
    volverLogin: () -> Unit
) {

    val context = LocalContext.current

    val database = remember {
        DatabaseHelper(context)
    }

    var nombre by rememberSaveable {
        mutableStateOf("")
    }

    var apellido by rememberSaveable {
        mutableStateOf("")
    }

    var correo by rememberSaveable {
        mutableStateOf("")
    }

    var usuario by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var confirmarPassword by rememberSaveable {
        mutableStateOf("")
    }

    var mensajeError by rememberSaveable {
        mutableStateOf("")
    }

    var registroCorrecto by rememberSaveable {
        mutableStateOf(false)
    }

    val azul = Color(0xFF2F80ED)
    val fondo = Color(0xFFF4F7FA)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 35.dp,
                bottom = 40.dp
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        LogoSafeMuni(
            grande = false
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Crear cuenta",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF17212B)
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        Text(
            text =
                "Completa tus datos para registrarte en SafeMuni.",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 13.sp,
            color = Color(0xFF7B8491)
        )

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        CampoRegistro(
            titulo = "Nombre",
            valor = nombre,
            placeholder =
                "Ingresa tu nombre",
            onValueChange = {

                nombre = it

                mensajeError = ""
            }
        )

        CampoRegistro(
            titulo = "Apellido",
            valor = apellido,
            placeholder =
                "Ingresa tu apellido",
            onValueChange = {

                apellido = it

                mensajeError = ""
            }
        )

        CampoRegistro(
            titulo =
                "Correo electrónico",
            valor = correo,
            placeholder =
                "correo@ejemplo.cl",
            keyboardType =
                KeyboardType.Email,
            onValueChange = {

                correo = it

                mensajeError = ""
            }
        )

        CampoRegistro(
            titulo = "Usuario",
            valor = usuario,
            placeholder =
                "Crea un nombre de usuario",
            onValueChange = {

                usuario = it

                mensajeError = ""
            }
        )

        CampoPassword(
            titulo = "Contraseña",
            valor = password,
            placeholder =
                "Crea una contraseña",
            onValueChange = {

                password = it

                mensajeError = ""
            }
        )

        CampoPassword(
            titulo =
                "Confirmar contraseña",
            valor = confirmarPassword,
            placeholder =
                "Repite tu contraseña",
            onValueChange = {

                confirmarPassword = it

                mensajeError = ""
            }
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Button(
            onClick = {

                mensajeError = ""

                if (
                    nombre.isBlank() ||
                    apellido.isBlank() ||
                    correo.isBlank() ||
                    usuario.isBlank() ||
                    password.isBlank() ||
                    confirmarPassword.isBlank()
                ) {

                    mensajeError =
                        "Debes completar todos los campos."

                } else if (
                    !Patterns.EMAIL_ADDRESS
                        .matcher(
                            correo.trim()
                        )
                        .matches()
                ) {

                    mensajeError =
                        "Ingresa un correo electrónico válido."

                } else if (
                    usuario.trim().length < 3
                ) {

                    mensajeError =
                        "El usuario debe tener al menos 3 caracteres."

                } else if (
                    password.length < 6
                ) {

                    mensajeError =
                        "La contraseña debe tener al menos 6 caracteres."

                } else if (
                    password != confirmarPassword
                ) {

                    mensajeError =
                        "Las contraseñas no coinciden."

                } else {

                    val resultado =
                        database.registrarUsuario(
                            nombre =
                                nombre.trim(),
                            apellido =
                                apellido.trim(),
                            correo =
                                correo.trim(),
                            usuario =
                                usuario.trim(),
                            password =
                                password
                        )

                    if (resultado) {

                        registroCorrecto =
                            true

                        mensajeError = ""

                    } else {

                        registroCorrecto =
                            false

                        mensajeError =
                            "El usuario o correo ya está registrado."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape =
                RoundedCornerShape(8.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = azul
                )
        ) {

            Text(
                text = "CREAR CUENTA",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        if (
            mensajeError.isNotEmpty()
        ) {

            Spacer(
                modifier =
                    Modifier.height(15.dp)
            )

            Surface(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(9.dp),
                color =
                    Color(0xFFFFE3E3)
            ) {

                Text(
                    text = mensajeError,
                    modifier =
                        Modifier.padding(15.dp),
                    fontSize = 13.sp,
                    color =
                        Color(0xFF8A2424)
                )
            }
        }

        if (registroCorrecto) {

            Spacer(
                modifier =
                    Modifier.height(15.dp)
            )

            Surface(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(9.dp),
                color =
                    Color(0xFFE1F5E5)
            ) {

                Column(
                    modifier =
                        Modifier.padding(15.dp)
                ) {

                    Text(
                        text =
                            "Cuenta creada correctamente",
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(0xFF236B35)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(5.dp)
                    )

                    Text(
                        text =
                            "Ahora puedes iniciar sesión con tu usuario.",
                        fontSize = 13.sp,
                        color =
                            Color(0xFF236B35)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text =
                "¿Ya tienes cuenta? Iniciar sesión",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = azul,
            modifier = Modifier.clickable {

                volverLogin()
            }
        )
    }
}

@Composable
fun CampoRegistro(
    titulo: String,
    valor: String,
    placeholder: String,
    keyboardType: KeyboardType =
        KeyboardType.Text,
    onValueChange: (String) -> Unit
) {

    Text(
        text = titulo,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF202833)
    )

    Spacer(
        modifier = Modifier.height(6.dp)
    )

    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {

            Text(
                text = placeholder,
                fontSize = 14.sp
            )
        },
        singleLine = true,
        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    keyboardType
            ),
        shape = RoundedCornerShape(9.dp)
    )

    Spacer(
        modifier = Modifier.height(15.dp)
    )
}

@Composable
fun CampoPassword(
    titulo: String,
    valor: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {

    Text(
        text = titulo,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF202833)
    )

    Spacer(
        modifier = Modifier.height(6.dp)
    )

    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {

            Text(
                text = placeholder,
                fontSize = 14.sp
            )
        },
        singleLine = true,
        visualTransformation =
            PasswordVisualTransformation(),
        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    KeyboardType.Password
            ),
        shape =
            RoundedCornerShape(9.dp)
    )

    Spacer(
        modifier = Modifier.height(15.dp)
    )
}