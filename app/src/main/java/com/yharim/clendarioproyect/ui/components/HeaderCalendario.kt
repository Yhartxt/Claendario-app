package com.yharim.clendarioproyect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HeaderCalendario(
    mesActual: YearMonth,
    onMesAnterior: () -> Unit,
    onMesSiguiente: () -> Unit
) {
    val nombreMes = mesActual.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercase() }
    val anio = mesActual.year

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$nombreMes $anio",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row {
            OutlinedButton(
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