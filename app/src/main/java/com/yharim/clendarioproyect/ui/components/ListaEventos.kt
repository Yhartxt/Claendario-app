package com.yharim.clendarioproyect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yharim.clendarioproyect.model.Evento

/*Zona nueva como DialigNuevoEvento, pero aqui se miran en la parte inferior del calendario
la info de lo horarios y eventos en sencilla pero aqui se muestra la lista de eventos ⁞⁝•ֱ̀␣̍•́⁝⁞*/

@Composable
fun ListaEventos(
    eventos: List<Evento>,
    modifier: Modifier = Modifier
) {
    if (eventos.isEmpty()) { //este if es para el mensaje de no hay eventos
        Text(
            text = "No hay eventos programados para este día.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(top = 8.dp)
        )
    } else { // aqui se muestra la lista de eventos
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(eventos) { evento ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = evento.titulo,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (evento.descripcion.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = evento.descripcion,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = evento.horarioTexto,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
