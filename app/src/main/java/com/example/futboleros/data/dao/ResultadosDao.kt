package com.example.futboleros.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.futboleros.data.entity.ResultadosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultadosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(resultados: ResultadosEntity): Long

    @Update
    suspend fun update (resultados: ResultadosEntity): Int

    @Delete
    suspend fun delete(resultados: ResultadosEntity): Int

    @Query(value = "SELECT * FROM resultados")
    fun getAllResultados(): Flow<List<ResultadosEntity>>

    @Query (value =  "SELECT * FROM resultados WHERE id = :id")
    fun getResultadosById(id: Int): Flow<ResultadosEntity?>

}