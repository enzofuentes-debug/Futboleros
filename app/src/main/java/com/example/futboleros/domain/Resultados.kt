package com.example.futboleros.domain

data class Resultados (
    val id: Int = 0,
    val fecha: String,
    val equipo1: String,
    val equipo2: String,
    val goles1: Int,
    val goles2: Int,
    val timestamp: Long = System.currentTimeMillis()
)
