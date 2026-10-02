package com.yharim.clendarioproyect.model

import java.time.LocalDate
import java.time.LocalTime

data class Evento(
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime
)