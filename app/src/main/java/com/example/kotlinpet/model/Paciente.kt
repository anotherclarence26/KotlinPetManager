package com.example.kotlinpet.model

data class Paciente(
    val id: String,
    val nombre: String,
    val especie: String,
    val edad: Int,
    val esUrgencia: Boolean,
    val contacto: String
)
