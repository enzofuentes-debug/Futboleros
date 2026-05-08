package com.example.futboleros.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resultados")
data class ResultadosEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val equipo1: String,
    val equipo2: String,
    val goles1: Int,
    val goles2: Int,
    val timestamp: Long = System.currentTimeMillis()
)
