package com.example.kotlinpet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kotlinpet.model.Paciente
import java.util.UUID

data class ErroresFormulario(
    val nombre: String? = null,
    val especie: String? = null,
    val edad: String? = null,
    val contacto: String? = null
) {
    val hayErrores: Boolean
        get() = nombre != null || especie != null || edad != null || contacto != null
}

class PacienteViewModel : ViewModel() {

    var pacientes by mutableStateOf<List<Paciente>>(emptyList())
        private set

    var nombre by mutableStateOf("")
        private set

    var especie by mutableStateOf("")
        private set

    var edad by mutableStateOf("")
        private set

    var contacto by mutableStateOf("")
        private set

    var esUrgencia by mutableStateOf(false)
        private set

    var errores by mutableStateOf(ErroresFormulario())
        private set

    fun onNombreChange(valor: String) {
        nombre = valor
        if (errores.nombre != null) errores = errores.copy(nombre = null)
    }

    fun onEspecieChange(valor: String) {
        especie = valor
        if (errores.especie != null) errores = errores.copy(especie = null)
    }

    fun onEdadChange(valor: String) {
        edad = valor.filter { it.isDigit() }.take(2)
        if (errores.edad != null) errores = errores.copy(edad = null)
    }

    fun onContactoChange(valor: String) {
        contacto = valor.filter { it.isDigit() || it == '+' || it == ' ' || it == '-' }.take(20)
        if (errores.contacto != null) errores = errores.copy(contacto = null)
    }

    fun onUrgenciaChange(valor: Boolean) {
        esUrgencia = valor
    }

    fun agregarPaciente() {
        val nombreLimpio = nombre.trim()
        val especieLimpia = especie.trim()
        val contactoLimpio = contacto.trim()
        val edadNumero = edad.toIntOrNull()

        val nuevosErrores = ErroresFormulario(
            nombre = if (nombreLimpio.isEmpty()) "Ingresa el nombre" else null,
            especie = if (especieLimpia.isEmpty()) "Ingresa la especie o raza" else null,
            edad = when {
                edadNumero == null -> "Edad inválida"
                edadNumero > 50 -> "Máximo 50"
                else -> null
            },
            contacto = if (contactoLimpio.count { it.isDigit() } < 7) "Mínimo 7 dígitos" else null
        )
        errores = nuevosErrores

        if (nuevosErrores.hayErrores) return
        val edadFinal = edadNumero ?: return

        val paciente = Paciente(
            id = UUID.randomUUID().toString(),
            nombre = nombreLimpio,
            especie = especieLimpia,
            edad = edadFinal,
            esUrgencia = esUrgencia,
            contacto = contactoLimpio
        )
        pacientes = listOf(paciente) + pacientes
        limpiarFormulario()
    }

    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contacto = ""
        esUrgencia = false
        errores = ErroresFormulario()
    }
}
