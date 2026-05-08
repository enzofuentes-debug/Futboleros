package com.example.futboleros.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.futboleros.data.dao.ResultadosDao
import com.example.futboleros.data.dao.UserDao
import com.example.futboleros.data.entity.ResultadosEntity
import com.example.futboleros.data.entity.UserEntity

@Database(entities =[ResultadosEntity::class, UserEntity::class], version = 2, exportSchema = false)

abstract class AppDataBase : RoomDatabase() {

    abstract fun resultadosDao():ResultadosDao
    abstract fun userDao():UserDao

    companion object{

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase (context: Context): AppDataBase{
            return  INSTANCE ?: synchronized(lock = this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    klass = AppDataBase::class.java,
                    name = "resultados_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}