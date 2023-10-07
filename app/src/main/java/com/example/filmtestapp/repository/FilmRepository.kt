package com.example.filmtestapp.repository

import androidx.paging.PagingData
import com.example.filmtestapp.data.FilmItem
import kotlinx.coroutines.flow.Flow


interface FilmRepository {

   fun getCurrentMovies(): Flow<PagingData<FilmItem>>

   fun getSearchedFilm(query: String): Flow<PagingData<FilmItem>>

   suspend fun getSearchedTitles(query: String): List<String>

   suspend fun addFavouriteFilm(filmItem: FilmItem)

   suspend fun deleteFavouriteFilm(filmId: Int)

   suspend fun isFilmFavourite(filmId: Int): Boolean
}