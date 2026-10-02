package com.yharim.clendarioproyect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yharim.clendarioproyect.model.formatearMesAnio
import java.time.YearMonth
/*lista de cambios:
de nuevo se movio toda la logica que habia en EventoArray.kt
aqui no habia mucha logica pero esta parte solo se dejo para la vista UI del topbar de mi calendario ᕦ(ツ)ᕤ*/

@Composable
fun HeaderCalendario(
    mesActual: YearMonth,
    onMesAnterior: () -> Unit,
    onMesSiguiente: () -> Unit
) {
    Row( //aqui encerramos toda la info de mi top bar como e mes y dia
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mesActual.formatearMesAnio(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row {
            OutlinedButton( //aqui aparece ya sea el mes anterior o el siguiente dependiendo del boton seleccionado
                onClick = onMesAnterior,
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Text("<")
            }
            OutlinedButton(onClick = onMesSiguiente) {
                Text(">")
            }
        }
    }
}
// parte a mejorar para la v1.0 del calendario