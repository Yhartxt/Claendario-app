package com.yharim.clendarioproyect.viewmodel

import androidx.lifecycle.ViewModel
import com.yharim.clendarioproyect.model.Evento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth
/*zona compuesta ya que estaba mal y dejaba de lado MVVM acorte el codigo lo hice mas limpio
 y arregle las correciones dandole mas senstido a mi codigo (⌐▨_▨)*/
data class CalendarioUiState(
    val mesActual: YearMonth = YearMonth.now(),
    val fechaSeleccionada: LocalDate = LocalDate.now(),
    val listaEventos: List<Evento> = emptyList(),
    val mostrarDialogo: Boolean = false
)

class CalendarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CalendarioUiState())
    
    val uiState: StateFlow<CalendarioUiState> = _uiState.asStateFlow()

    fun cambiarMes(delta: Long) {
        _uiState.update { currentState ->
            currentState.copy(mesActual = currentState.mesActual.plusMonths(delta))
        }
    }

    fun seleccionarFecha(nuevaFecha: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(fechaSeleccionada = nuevaFecha)
        }
    }

    fun abrirDialogo() {
        _uiState.update { currentState ->
            currentState.copy(mostrarDialogo = true)
        }
    }

    fun cerrarDialogo() {
        _uiState.update { currentState ->
            currentState.copy(mostrarDialogo = false)
        }
    }

    fun agregarEvento(nuevoEvento: Evento) {
        _uiState.update { currentState ->
            currentState.copy(
                listaEventos = currentState.listaEventos + nuevoEvento,
                mostrarDialogo = false
            )
        }
    }
}
