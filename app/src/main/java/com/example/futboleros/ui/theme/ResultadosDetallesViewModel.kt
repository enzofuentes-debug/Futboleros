package com.example.futboleros.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.futboleros.data.entity.ResultadosRepository
import com.example.futboleros.domain.Resultados
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
class ResultadosDetallesViewModel(
    private val ResultadosRepository: ResultadosRepository,
    contactId: Int
): ViewModel() {

    val resultados: StateFlow<Resultados?> = ResultadosRepository
        .getResultadosById(contactId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            null
        )

    fun updateResultados(name: String, phone: String){
        val currentResultados = resultados.value ?: return
        viewModelScope.launch {
            ResultadosRepository.updateResultados(currentResultados.copy(name = name, phone = phone))
        }
    }

    fun deleteContact (onDeleted:()  -> Unit){
        val currentResultados = resultados.value ?: return
        viewModelScope.launch {
            ResultadosRepository.deleteResultados(currentResultados)
            onDeleted()
        }
    }


}

class  ResultadosDetailViewModelFactory(
    private val repository: ResultadosRepository,
    private val resultadosId: Int
): ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create (modelClass: Class <T>): T {

        if (modelClass.isAssignableFrom(ResultadosDetallesViewModel::class.java)){
            return ResultadosDetallesViewModel (repository, resultadosId) as T
        }

        throw IllegalArgumentException ("Unknown ViewModel class")

    }
}

 */