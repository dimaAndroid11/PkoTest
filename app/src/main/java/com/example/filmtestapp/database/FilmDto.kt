package com.example.filmtestapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "film_table")
data class FilmDto(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,
   val itemId: Int,
)