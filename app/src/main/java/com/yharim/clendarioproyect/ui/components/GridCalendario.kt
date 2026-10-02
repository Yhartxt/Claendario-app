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
import com.yharim.clendarioproyect.model.DIAS_SEMANA_TITULOS
import com.yharim.clendarioproyect.model.desplazamientoInicio
import com.yharim.clendarioproyect.model.obtenerDiaCalendario
import com.yharim.clendarioproyect.model.totalDias
import java.time.LocalDate
import java.time.YearMonth
/*Lista de cambios:
Se movio toda la logica de mis dias despues del regaño del profe xd
toda mi parte logica se hace en base a llamados desde EventoArray que se encarga de mi logica
Contexto de archivo: aqui hacemos los grids y le damos formato a mi calendario al igual que a los dias (⊙_•)*/

@Composable
fun GridCalendario(
    mesActual: YearMonth,
    fechaSeleccionada: LocalDate,
    onFechaSeleccionada: (LocalDate) -> Unit
) {
    val desplazamientoInicio = mesActual.desplazamientoInicio
    val totalDias = mesActual.totalDias

    Column {
        // Fila con nombres de los días (Lun, Mar...) desde EventoArray.kt
        Row(modifier = Modifier.fillMaxWidth()) {
            DIAS_SEMANA_TITULOS.forEach { dia ->
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
            // 1. Casillas vacías antes del día 1, si es que hay, pero en caso de que si se ponen los dias vacios
            items(desplazamientoInicio) {
                Box(modifier = Modifier.size(40.dp))
            }

            // 2. Días reales del mes (Toda la información del día la calcula EventoArray.kt)
            items(totalDias) { numeroDia ->
                val dia = mesActual.obtenerDiaCalendario(numeroDia, fechaSeleccionada)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                dia.esSeleccionado -> MaterialTheme.colorScheme.primary
                                dia.esHoy -> MaterialTheme.colorScheme.primaryContainer
                                else -> Color.Transparent
                            }
                        )
                        .clickable { onFechaSeleccionada(dia.fecha) }
                ) {
                    Text(
                        text = dia.numeroDia.toString(),
                        color = when {
                            dia.esSeleccionado -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                        fontWeight = if (dia.esHoy || dia.esSeleccionado) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
