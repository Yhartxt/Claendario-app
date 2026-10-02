package com.yharim.clendarioproyect.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yharim.clendarioproyect.model.Evento
import com.yharim.clendarioproyect.model.FormularioNuevoEvento
import java.time.LocalDate
/*Parte completamente nueva para crear mis card donde se ponen la info del evento asi c
 como la seleccion de dia y descripciones de evento bueno solo en lo grafico (o°.°o)*/
@Composable
fun DialogoNuevoEvento(
    fechaSeleccionada: LocalDate,
    listaEventos: List<Evento>,
    onDismiss: () -> Unit,
    onGuardarEvento: (Evento) -> Unit
) {
    var formulario by remember { mutableStateOf(FormularioNuevoEvento()) }

    AlertDialog( //aqui inicia mi card
        onDismissRequest = onDismiss,
        title = { Text("Nuevo Evento") },// aqui es donde aparece boton con el texto para agregar eventos
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    //zona de texto para agregar titulo
                    value = formulario.titulo,
                    onValueChange = { nuevoTitulo ->
                        formulario = formulario.copy(titulo = nuevoTitulo, errorTexto = null)
                    },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(//zona de texto para agregar descripcion
                    value = formulario.descripcion,
                    onValueChange = { nuevaDesc ->
                        formulario = formulario.copy(descripcion = nuevaDesc)
                    },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(//zona de texto para agregar hora de inicio y fin
                        value = formulario.horaInicioTexto,
                        onValueChange = { nuevaHora ->
                            formulario = formulario.copy(horaInicioTexto = nuevaHora, errorTexto = null)
                        },
                        label = { Text("Inicio (HH:mm)") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = formulario.horaFinTexto,
                        onValueChange = { nuevaHora ->
                            formulario = formulario.copy(horaFinTexto = nuevaHora, errorTexto = null)
                        },
                        label = { Text("Fin (HH:mm)") },
                        modifier = Modifier.weight(1f)
                    )
                }
                formulario.errorTexto?.let { error ->//zona de texto para mostrar errores
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {//aqui esta la parte de avisos al usuario, guardar y cancelar
            Button(
                onClick = {
                    // Toda la validación, choque de horarios y creación del evento se delega al modelo EventoArray.kt
                    val (formularioActualizado, eventoValido) = formulario.procesarFormulario(fechaSeleccionada, listaEventos)
                    formulario = formularioActualizado

                    if (eventoValido != null) {
                        onGuardarEvento(eventoValido)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
