package com.example.futboleros.ui.theme


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.futboleros.data.entity.ResultadosRepository
import com.example.futboleros.domain.Resultados
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ResultadosViewModel (
    private val repository: ResultadosRepository
): ViewModel(){


    val resultados = repository
        .getAllResultados()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun addResultados(resultados: Resultados){
        viewModelScope.launch {
            repository.insertResultados(resultados)
        }
    }

    class ResultadosViewModelFactory (private val repository: ResultadosRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create (modelClass: Class<T>): T {

            if(modelClass.isAssignableFrom(ResultadosViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ResultadosViewModel(repository) as T
            }

            throw IllegalArgumentException ("Unknown ViewModel class")

        }
    }


}

