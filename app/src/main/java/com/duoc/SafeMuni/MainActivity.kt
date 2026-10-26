package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.duoc.SafeMuni.ui.NuevoReporte
import com.duoc.SafeMuni.ui.theme.SafeMuniTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeMuniTheme {
                NuevoReporte()
            }
        }
    }
}

