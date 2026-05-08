package com.example.futboleros.data.entity


import com.example.futboleros.data.dao.ResultadosDao
import com.example.futboleros.domain.Resultados
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ResultadosRepository(
    private val resultadoDao: ResultadosDao
)
{

    fun getAllResultados(): Flow<List<Resultados>>{
        return resultadoDao.getAllResultados().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getResultadosById(id: Int): Flow<Resultados?>{
        return resultadoDao.getResultadosById(id).map{ it?.toDomain()}
    }

    suspend fun  insertResultados (resultados: Resultados){
        resultadoDao.insert(resultados.toEntity())
    }

    suspend fun  updateResultados (resultados: Resultados){
        resultadoDao.update(resultados.toEntity())
    }

    suspend fun  deleteResultados (resultados: Resultados){
        resultadoDao.delete(resultados.toEntity())
    }

}

fun ResultadosEntity.toDomain() = Resultados (
    id = id,
    fecha = fecha,
    equipo1 = equipo1,
    equipo2 = equipo2,
    goles1 = goles1,
    goles2 = goles2,
    timestamp = timestamp
)

fun Resultados.toEntity() = ResultadosEntity (
    id = id,
    fecha = fecha,
    equipo1 = equipo1,
    equipo2 = equipo2,
    goles1 = goles1,
    goles2 = goles2,
    timestamp = timestamp
)
