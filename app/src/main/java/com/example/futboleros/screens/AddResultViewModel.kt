package com.example.futboleros.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.futboleros.data.entity.ResultadosRepository
import com.example.futboleros.domain.Resultados
import kotlinx.coroutines.launch


class AddResultViewModel(private val repository: ResultadosRepository?) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun saveResult(fecha: String, equipo1: String, equipo2: String, goles1: String, goles2: String) {
        if (fecha.isBlank() || equipo1.isBlank() || equipo2.isBlank() || goles1.isBlank() || goles2.isBlank()) {
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val g1 = goles1.toIntOrNull() ?: 0
                val g2 = goles2.toIntOrNull() ?: 0
                
                // Save to Room
                repository?.insertResultados(
                    Resultados(
                        fecha = fecha,
                        equipo1 = equipo1,
                        equipo2 = equipo2,
                        goles1 = g1,
                        goles2 = g2
                    )
                )
                
                _isSuccess.value = true
            } catch (e: Exception) {
                Log.e("AddResultViewModel", "Error saving result", e)
                _isSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetSuccess() {
        _isSuccess.value = false
    }

    class Factory(private val repository: ResultadosRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddResultViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddResultViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
