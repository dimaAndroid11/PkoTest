package com.example.filmtestapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FilmDto::class], version = 2, exportSchema = false)
abstract class FilmDataBase : RoomDatabase() {

   abstract fun filmDao(): FilmDao
}

object FilmDatabaseConstructor {

   fun create(context: Context): FilmDataBase =
      Room.databaseBuilder(
         context,
         FilmDataBase::class.java,
         "film_table"
      ).fallbackToDestructiveMigration().build()
}