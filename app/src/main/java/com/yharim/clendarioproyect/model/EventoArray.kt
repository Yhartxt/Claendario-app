package com.yharim.clendarioproyect.model

import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
/*Este mi archivo donde se movio toda la logica y mi codigo principal para el calendario, muchos de estos bloques de codigo
 son usados en varios de los archivos(⌐■_■)*/

/*Parte #1:
 aqui se declararon mis funciones para mis eventos osea esta parte esta echa y es
 correspondiente a mi logica del archivo DialogNuevoEvento, e igual se en otros lugares, como en lista de eventos (•̀ᴗ•́)و*/
data class Evento(
    val titulo: String,
    val descripcion: String,
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime
) {
    // Formateo de la hora para visualización
    val horarioTexto: String
        get() = "$horaInicio - $horaFin"

    // Comprueba si el evento pertenece a una fecha dada
    fun esDelDia(fechaConsultada: LocalDate): Boolean {
        return this.fecha == fechaConsultada
    }

    // Comprueba si este evento se traslapa en horario con otro evento el mismo día
    fun seTraslapaCon(otro: Evento): Boolean {
        if (this.fecha != otro.fecha) return false
        return this.horaInicio < otro.horaFin && otro.horaInicio < this.horaFin
    }

    companion object {
        // Lógica de creación y parseo de horas a partir de texto
        fun crearDesdeTexto(
            titulo: String,
            descripcion: String,
            fecha: LocalDate,
            horaInicioTexto: String,
            horaFinTexto: String
        ): Evento {
            val horaInicio = try {
                LocalTime.parse(horaInicioTexto)
            } catch (_: Exception) {
                LocalTime.of(9, 0)
            }
            val horaFin = try {
                LocalTime.parse(horaFinTexto)
            } catch (_: Exception) {
                LocalTime.of(10, 0)
            }
            return Evento(
                titulo = titulo.trim(),
                descripcion = descripcion.trim(),
                fecha = fecha,
                horaInicio = horaInicio,
                horaFin = horaFin
            )
        }
    }
}

/*bien aqui le damos formato y forma a los eventos descritos anteriormente,
 esta parte es mas para rellenar los textos antes de que el usuario ponga algo ( ‾ʖ̫‾)*/
data class FormularioNuevoEvento(
    val titulo: String = "",
    val descripcion: String = "",
    val horaInicioTexto: String = "09:00",
    val horaFinTexto: String = "10:00",
    val errorTexto: String? = null
) {
    fun esValido(): Boolean {
        return titulo.isNotBlank()
    }

    fun aEvento(fecha: LocalDate): Evento {
        return Evento.crearDesdeTexto(
            titulo = titulo,
            descripcion = descripcion,
            fecha = fecha,
            horaInicioTexto = horaInicioTexto,
            horaFinTexto = horaFinTexto
        )
    }

    /*parte final y zona de validacion, aqui validamos si el usuario no tiene un evento ya puesto en su calendario o tiene
    el titulo vacio ʕ·ᴥ·ʔ*/
    fun procesarFormulario(
        fecha: LocalDate,
        listaEventosExistentes: List<Evento>
    ): Pair<FormularioNuevoEvento, Evento?> {
        if (!esValido()) {
            return Pair(this.copy(errorTexto = "El título no puede estar vacío"), null)
        }

        val eventoProcesado = aEvento(fecha)

        if (listaEventosExistentes.tieneConflictoDeHorario(eventoProcesado)) {
            return Pair(this.copy(errorTexto = "Ya tienes un evento programado en este horario"), null)
        }

        return Pair(this.copy(errorTexto = null), eventoProcesado)
    }
}

/*Part 2: Aqui esta la parte que le da formato a los dias del calendario,
bien lo que se hizo aqui de primera instancia fue darle logica a los dias, y usando la libreria de java.time
se pudo hacer este trabajo de manera mas facil, ya que me da la info necesaria para este trabajo ʘ‿ʘ */
data class DiaCalendario(
    //aqui se declaran los dias del calendario
    val numeroDia: Int,
    val fecha: LocalDate,
    val esSeleccionado: Boolean,
    val esHoy: Boolean
)

//bien aqui primero declaramos los dias de lunes a domigos los 7 dias de la semana
val DIAS_SEMANA_TITULOS = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

//ahora usando la libreria sacamos los formatos de la fecha vemos que dia de la semana, el numero dle dia en texto
fun LocalDate.formatearTextoCompleto(): String {
    val diaNombre = this.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercase() }
    val diaNumero = this.dayOfMonth
    val mesNombre = this.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
    return "$diaNombre, $diaNumero de $mesNombre"
}

//aqui sacamos el año en texto y el mes en texto
fun YearMonth.formatearMesAnio(): String {
    val nombreMes = this.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
        .replaceFirstChar { it.uppercase() }
    return "$nombreMes ${this.year}"
}

/*esta parte sirve para que dependiendo del dia en que inicie el mes por ejemplo jueves
 los dias anterios a este mes no tengan numero y aparezcan como casillas vacias (OvO)*/
val YearMonth.desplazamientoInicio: Int
    get() = this.atDay(1).dayOfWeek.value - 1

//aqui se ve el total de dias que tiene el mes
val YearMonth.totalDias: Int
    get() = this.lengthOfMonth()

fun YearMonth.obtenerFecha(numeroDia: Int): LocalDate {
    return this.atDay(numeroDia)
}

// Calcula toda la información lógica de la casilla del día
fun YearMonth.obtenerDiaCalendario(indiceBase0: Int, fechaSeleccionada: LocalDate): DiaCalendario {
    val diaActual = indiceBase0 + 1
    val fecha = this.obtenerFecha(diaActual)
    return DiaCalendario(
        numeroDia = diaActual,
        fecha = fecha,
        esSeleccionado = (fecha == fechaSeleccionada),
        esHoy = (fecha == LocalDate.now())
    )
}

//parte 3: Extensiones y funciones de utilidad para la lista/arreglo de eventos
fun List<Evento>.obtenerEventosDelDia(fecha: LocalDate): List<Evento> {
    return this.filter { it.esDelDia(fecha) }
        .sortedBy { it.horaInicio }
}

fun List<Evento>.tieneConflictoDeHorario(nuevoEvento: Evento): Boolean {
    return this.any { it.seTraslapaCon(nuevoEvento) }
}
