package com.yharim.clendarioproyect.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yharim.clendarioproyect.model.Evento
import java.time.LocalDate
import java.time.YearMonth

class CalendarioViewModel : ViewModel() {

    // Estados observables por la UI
    var fechaSeleccionada = mutableStateOf(LocalDate.now())
    var mesActual = mutableStateOf(YearMonth.now())
    var listaEventos = mutableStateListOf<Evento>()
    var mostrarDialogo = mutableStateOf(false)

    // Funciones para modificar el estado (Lógica de negocio)
    fun cambiarMes(delta: Long) {
        mesActual.value = mesActual.value.plusMonths(delta)
    }

    fun seleccionarFecha(nuevaFecha: LocalDate) {
        fechaSeleccionada.value = nuevaFecha
    }

    fun agregarEvento(nuevoEvento: Evento) {
        listaEventos.add(nuevoEvento)
    }
}
