package com.yharim.clendarioproyect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.yharim.clendarioproyect.model.formatearTextoCompleto
import com.yharim.clendarioproyect.model.obtenerEventosDelDia
import com.yharim.clendarioproyect.ui.components.HeaderCalendario
import com.yharim.clendarioproyect.ui.components.GridCalendario
import com.yharim.clendarioproyect.ui.components.ListaEventos
import com.yharim.clendarioproyect.ui.components.DialogoNuevoEvento
import com.yharim.clendarioproyect.viewmodel.CalendarioViewModel

@Composable
fun CalendarioScreen(
    viewModel: CalendarioViewModel = viewModel()
) {
    /*Estado inmutuable puesto aqui, cambiado en el viewModel
    Igual se movio toda la logica a EventoArray.kt
    los val dejados e if son para el manejo de errores no tiene nada que ver con la logica a si que no se movieron (⌐■_■)*/
    val uiState by viewModel.uiState.collectAsState()

    val mesActual = uiState.mesActual
    val fechaSeleccionada = uiState.fechaSeleccionada
    val listaEventos = uiState.listaEventos
    val mostrarDialogo = uiState.mostrarDialogo

    val eventosDelDia = listaEventos.obtenerEventosDelDia(fechaSeleccionada)

    if (mostrarDialogo) {
        DialogoNuevoEvento(
            fechaSeleccionada = fechaSeleccionada,
            listaEventos = listaEventos,
            onDismiss = { viewModel.cerrarDialogo() },
            onGuardarEvento = { nuevoEvento ->
                viewModel.agregarEvento(nuevoEvento)
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.abrirDialogo() },
                icon = { Icon(Icons.Default.Add, contentDescription = "Agregar evento") },
                text = { Text("Nuevo evento") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // 1. Encabezado
            HeaderCalendario(
                mesActual = mesActual,
                onMesAnterior = { viewModel.cambiarMes(-1) },
                onMesSiguiente = { viewModel.cambiarMes(1) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. Cuadrícula
            GridCalendario(
                mesActual = mesActual,
                fechaSeleccionada = fechaSeleccionada,
                onFechaSeleccionada = { nuevaFecha ->
                    viewModel.seleccionarFecha(nuevaFecha)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Fecha seleccionada
            Text(
                text = fechaSeleccionada.formatearTextoCompleto(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4. Lista de eventos del día
            ListaEventos(
                eventos = eventosDelDia,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
