package com.example.filmtestapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.filmtestapp.data.FilmItem
import com.example.filmtestapp.dataSource.CurrentPagingDataSource
import com.example.filmtestapp.dataSource.SearchPagingDataSource
import com.example.filmtestapp.database.FilmDao
import com.example.filmtestapp.network.ApiService
import com.example.filmtestapp.utils.mapToFilmDto
import kotlinx.coroutines.flow.Flow

class FilmRepositoryImpl(
   private val apiService: ApiService,
   private val filmDao: FilmDao
): FilmRepository {

   override fun getCurrentMovies(): Flow<PagingData<FilmItem>> = Pager(
         config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
         pagingSourceFactory = { CurrentPagingDataSource(apiService = apiService)}
      ).flow

   override fun getSearchedFilm(query: String): Flow<PagingData<FilmItem>> = Pager(
         config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
         pagingSourceFactory = { SearchPagingDataSource(query = query,apiService = apiService)}
      ).flow

   override suspend fun getSearchedTitles(query: String): List<String> =
      apiService.searchMovie(query = query)?.results?.map { it.title } ?: emptyList()

   override suspend fun addFavouriteFilm(filmItem: FilmItem) =
      filmDao.addFavouriteFilm(filmDto = filmItem.mapToFilmDto())

   override suspend fun deleteFavouriteFilm(filmId: Int) =
      filmDao.deleteFavouriteFilm(filmId = filmId)

   override suspend fun isFilmFavourite(filmId: Int): Boolean =
      filmDao.isFilmFavourite(filmId = filmId) == 1

   companion object {
      private const val DEFAULT_PAGE_SIZE = 20
   }
}