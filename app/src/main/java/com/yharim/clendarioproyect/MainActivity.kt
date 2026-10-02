package com.yharim.clendarioproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yharim.clendarioproyect.ui.screens.CalendarioScreen
import com.yharim.clendarioproyect.ui.theme.ClendarioProyectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClendarioProyectTheme {
                //se quito el sacafle  /|(•ω•)/|\
                    CalendarioScreen()
            }
        }
    }
}