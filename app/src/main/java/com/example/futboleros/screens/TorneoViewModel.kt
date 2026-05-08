package com.example.futboleros.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.futboleros.R
import com.example.futboleros.data.entity.ResultadosRepository
import kotlinx.coroutines.launch

data class TeamStats(
    val name: String,
    var played: Int = 0,
    var points: Int = 0,
    var goalsFor: Int = 0,
    var goalsAgainst: Int = 0
) {
    val goalDifference: Int get() = goalsFor - goalsAgainst
}

class TorneoViewModel(private val repository: ResultadosRepository) : ViewModel() {

    private val _tablaPosiciones = MutableLiveData<List<equipo>>(emptyList())
    val tablaPosiciones: LiveData<List<equipo>> = _tablaPosiciones

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val allTeams = listOf(
        "Ajax", "Juventud Unida", "Independiente", "Ferro", "Athenas", 
        "Mambas Negras", "Chelsea", "El Pincha", "La Fiore", 
        "Virgen del Valle", "Callejeras", "La Roma"
    )

    init {
        fetchResultadosAndCalculateTable()
    }

    fun fetchResultadosAndCalculateTable() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAllResultados().collect { resultadosList ->
                val statsMap = allTeams.associateWith { TeamStats(it) }.toMutableMap()

                resultadosList.forEach { match ->
                    val e1 = match.equipo1
                    val e2 = match.equipo2
                    val g1 = match.goles1
                    val g2 = match.goles2

                    // Cálculo automático de puntos: Victoria 3, Empate 1, Derrota 0
                    val points1 = when {
                        g1 > g2 -> 3
                        g1 == g2 -> 1
                        else -> 0
                    }
                    val points2 = when {
                        g2 > g1 -> 3
                        g2 == g1 -> 1
                        else -> 0
                    }

                    statsMap[e1]?.let {
                        it.played += 1
                        it.points += points1
                        it.goalsFor += g1
                        it.goalsAgainst += g2
                    }

                    statsMap[e2]?.let {
                        it.played += 1
                        it.points += points2
                        it.goalsFor += g2
                        it.goalsAgainst += g1
                    }
                }

                val sortedTeams = statsMap.values
                    .sortedWith(compareByDescending<TeamStats> { it.points }
                        .thenByDescending { it.goalDifference }
                        .thenByDescending { it.goalsFor })
                    .mapIndexed { index, stats ->
                        equipo(
                            pos = index + 1,
                            logo = getTeamLogo(stats.name),
                            equipo = stats.name,
                            jp = stats.played,
                            diff = (if (stats.goalDifference > 0) "+" else "") + stats.goalDifference.toString(),
                            pts = stats.points
                        )
                    }

                _tablaPosiciones.value = sortedTeams
                _isLoading.value = false
            }
        }
    }

    private fun getTeamLogo(teamName: String): Int {
        return when (teamName) {
            "Ajax" -> R.drawable.equipo_ajax
            "Juventud Unida" -> R.drawable.equipo_juventudunida
            "Independiente" -> R.drawable.equipo_independiente
            "Ferro" -> R.drawable.equipo_ferro
            "Athenas" -> R.drawable.equipo_athenas
            "Mambas Negras" -> R.drawable.equipo_mambasnegras
            "Chelsea" -> R.drawable.equipo_chelsea
            "El Pincha" -> R.drawable.equipo_elpincha
            "La Fiore" -> R.drawable.equipo_lafiore
            "Virgen del Valle" -> R.drawable.equipo_virgendelvalle
            "Callejeras" -> R.drawable.equipo_callejeras
            "La Roma" -> R.drawable.equipo_laroma
            else -> 0
        }
    }

    class Factory(private val repository: ResultadosRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TorneoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TorneoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
