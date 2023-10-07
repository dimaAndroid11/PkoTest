package com.example.filmtestapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addFavouriteFilm(filmDto: FilmDto)

   @Query("SELECT EXISTS (SELECT 1 FROM film_table WHERE itemId = :filmId)")
   suspend fun isFilmFavourite(filmId: Int): Int

   @Query("DELETE FROM film_table WHERE itemId = :filmId")
   suspend fun deleteFavouriteFilm(filmId: Int)
}