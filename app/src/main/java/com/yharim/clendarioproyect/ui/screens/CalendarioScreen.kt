package com.yharim.clendarioproyect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.yharim.clendarioproyect.ui.components.HeaderCalendario
import com.yharim.clendarioproyect.ui.components.GridCalendario
import com.yharim.clendarioproyect.viewmodel.CalendarioViewModel

import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarioScreen(
    viewModel: CalendarioViewModel = viewModel()
) {
    val mesActual = viewModel.mesActual.value
    val fechaSeleccionada = viewModel.fechaSeleccionada.value
    val listaEventos = viewModel.listaEventos

    val diaNombre = fechaSeleccionada.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercase() }
    val diaNumero = fechaSeleccionada.dayOfMonth
    val mesNombre = fechaSeleccionada.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
    val fechaFormateadaTexto = "$diaNombre, $diaNumero de $mesNombre"

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.mostrarDialogo.value = true },
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
                text = fechaFormateadaTexto,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 4. Espacio para lista de tareas del día
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val eventosDelDia = listaEventos.filter { it.fecha == fechaSeleccionada }

                if (eventosDelDia.isEmpty()) {
                    Text(
                        text = "No hay eventos programados para este día.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}