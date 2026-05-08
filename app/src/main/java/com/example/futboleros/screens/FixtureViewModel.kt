package com.example.futboleros.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.futboleros.data.entity.ResultadosRepository
import kotlinx.coroutines.flow.map

class FixtureViewModel(repository: ResultadosRepository?) : ViewModel() {

    val fixtureData: LiveData<List<Fecha>> = repository?.getAllResultados()?.map { resultadosList ->
        resultadosList.groupBy { it.fecha }
            .map { (fechaTitle, matches) ->
                Fecha(
                    title = fechaTitle,
                    matches = matches.map { match ->
                        Match(
                            team1 = match.equipo1,
                            team1Logo = 0,
                            score = "${match.goles1} - ${match.goles2}",
                            team2 = match.equipo2,
                            team2Logo = 0
                        )
                    }
                )
            }
            .sortedByDescending { fecha ->
                // Extrae el número de la cadena (ej: "Fecha 6" -> 6) para ordenar de mayor a menor
                fecha.title.filter { it.isDigit() }.toIntOrNull() ?: 0
            }
    }?.asLiveData() ?: MutableLiveData(emptyList())

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    class Factory(private val repository: ResultadosRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FixtureViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FixtureViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
