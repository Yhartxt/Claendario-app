package com.yharim.clendarioproyect.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

//zona logica donde se calcula las fechas
@Composable
fun GridCalendario(
    mesActual: YearMonth,
    fechaSeleccionada: LocalDate,
    onFechaSeleccionada: (LocalDate) -> Unit
) {
    val diasSemana = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

    // Cálculo de fechas
    val primerDiaDelMes = mesActual.atDay(1)
    val totalDias = mesActual.lengthOfMonth()
    // Convertimos el día de la semana (1=Lunes, 7=Domingo) a índice base 0
    val desplazamientoInicio = primerDiaDelMes.dayOfWeek.value - 1

    Column {
        // Fila con nombres de los días (Lun, Mar...)
        Row(modifier = Modifier.fillMaxWidth()) {
            diasSemana.forEach { dia ->
                Text(
                    text = dia,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cuadrícula de números
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.height(280.dp)
        ) {
            // 1. Casillas vacías antes del día 1
            items(desplazamientoInicio) {
                Box(modifier = Modifier.size(40.dp))
            }

            // 2. Días reales del mes
            items(totalDias) { numeroDia ->
                val diaActual = numeroDia + 1
                val fechaTarjeta = mesActual.atDay(diaActual)
                val esSeleccionado = fechaTarjeta == fechaSeleccionada
                val esHoy = fechaTarjeta == LocalDate.now()

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                esSeleccionado -> MaterialTheme.colorScheme.primary
                                esHoy -> MaterialTheme.colorScheme.primaryContainer
                                else -> Color.Transparent
                            }
                        )
                        .clickable { onFechaSeleccionada(fechaTarjeta) }
                ) {
                    Text(
                        text = diaActual.toString(),
                        color = when {
                            esSeleccionado -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                        fontWeight = if (esHoy || esSeleccionado) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}