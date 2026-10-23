package com.duoc.SafeMuni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.duoc.SafeMuni.ui.Camara
import com.duoc.SafeMuni.ui.NuevoReporte
import com.duoc.SafeMuni.ui.theme.SafeMuniTheme
import com.duoc.SafeMuni.viewmodel.NuevoReporteViewModel

class MainActivity : ComponentActivity() {

    private val viewModelReport = NuevoReporteViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafeMuniTheme {
                //NuevoReporte(viewModelReport)
                Camara()
            }
        }
    }
}

