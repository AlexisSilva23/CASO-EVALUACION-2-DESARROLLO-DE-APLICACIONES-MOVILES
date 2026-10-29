package com.duoc.SafeMuni.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onNavegarA: (String) -> Unit,
    onCerrarSesion: () -> Unit = { onNavegarA("Login") }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Perfil de Usuario",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "SafeMuni - Seguridad Ciudadana",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Cuenta",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column {
                    HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BarraItemPersonalizada(
                            label = "Inicio",
                            isSelected = false,
                            onClick = { onNavegarA("Inicio") }
                        )
                        BarraItemPersonalizada(
                            label = "Reportar",
                            isSelected = false,
                            onClick = { onNavegarA("Reportar") }
                        )
                        BarraItemPersonalizada(
                            label = "Mis casos",
                            isSelected = false,
                            onClick = { onNavegarA("Mis casos") }
                        )
                        BarraItemPersonalizada(
                            label = "Perfil",
                            isSelected = true,
                            onClick = { }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF4F6F9))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Tarjeta de Avatar y Nombre
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0D47A1)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Vecino Demo",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "Rol: Persona / Vecino",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = Color(0xFF1565C0),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // 2. Resumen Estadístico
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EstadisticaItem(
                    modifier = Modifier.weight(1f),
                    titulo = "Reportes",
                    valor = "4",
                    icono = Icons.Default.Assignment,
                    color = Color(0xFF0288D1)
                )
                EstadisticaItem(
                    modifier = Modifier.weight(1f),
                    titulo = "Resueltos",
                    valor = "2",
                    icono = Icons.Default.CheckCircle,
                    color = Color(0xFF2E7D32)
                )
                EstadisticaItem(
                    modifier = Modifier.weight(1f),
                    titulo = "En Proceso",
                    valor = "2",
                    icono = Icons.Default.Pending,
                    color = Color(0xFFED6C02)
                )
            }

            // 3. Información Detallada
            Text(
                text = "Datos de la cuenta",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 4.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    ItemInfoRow(
                        icono = Icons.Default.Email,
                        titulo = "Correo Electrónico",
                        subtitulo = "vecino.demo@safemuni.cl"
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF1F5F9))
                    ItemInfoRow(
                        icono = Icons.Default.Phone,
                        titulo = "Teléfono de Contacto",
                        subtitulo = "+56 9 1234 5678"
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF1F5F9))
                    ItemInfoRow(
                        icono = Icons.Default.LocationOn,
                        titulo = "Comuna / Cuadrante",
                        subtitulo = "Sector Centro - Cuadrante 4"
                    )
                }
            }

            // 4. Botón de Cerrar Sesión
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun BarraItemPersonalizada(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colorActivo = Color(0xFF2563EB)
    val colorInactivoCirculo = Color(0xFFCBD5E1)
    val colorInactivoTexto = Color(0xFF64748B)

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(if (isSelected) colorActivo else colorInactivoCirculo)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) colorActivo else colorInactivoTexto
        )
    }
}

@Composable
private fun EstadisticaItem(
    modifier: Modifier = Modifier,
    titulo: String,
    valor: String,
    icono: ImageVector,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icono, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = valor, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Text(text = titulo, fontSize = 11.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
private fun ItemInfoRow(
    icono: ImageVector,
    titulo: String,
    subtitulo: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = Color(0xFF0D47A1),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = titulo, fontSize = 12.sp, color = Color(0xFF64748B))
            Text(text = subtitulo, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1E293B))
        }
    }
}


