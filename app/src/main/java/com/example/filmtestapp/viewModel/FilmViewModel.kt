package com.example.filmtestapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmtestapp.data.FilmItem
import com.example.filmtestapp.repository.FilmRepository
import com.example.filmtestapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class FilmViewModel(
   private val filmRepository: FilmRepository
): ViewModel() {

   private var _filmFlow = MutableStateFlow<Resource<PagingData<FilmItem>>>(Resource.Empty())
   val filmFlow: StateFlow<Resource<PagingData<FilmItem>>> get() = _filmFlow.asStateFlow()

   private var _searchedFilmFlow = MutableStateFlow<PagingData<FilmItem>>(PagingData.empty())
   val searchedFilm: StateFlow<PagingData<FilmItem>> = _searchedFilmFlow.asStateFlow()

   fun getCurrentFilmList() {
      viewModelScope.launch(Dispatchers.IO) {
         _filmFlow.value = Resource.Loading()
         try {
            val currentFilmList = filmRepository.getCurrentMovies().cachedIn(this).first()
            _filmFlow.value = Resource.Success(currentFilmList)
         } catch (e: Exception){
            val errorMessage = e.message ?: DEFAULT_ERROR_MESSAGE
            _filmFlow.value = Resource.Error(errorMessage)
         }
      }
   }

   fun addFavouriteFilm(filmItem: FilmItem){
      viewModelScope.launch(Dispatchers.IO) {
         filmRepository.addFavouriteFilm(filmItem = filmItem)
      }
   }

   fun isFilmFavourite(filmId: Int): Flow<Boolean> {
      return flow{
         val isFavourite = filmRepository.isFilmFavourite(filmId)
         emit(isFavourite)
      }.flowOn(Dispatchers.IO)
   }

   fun deleteFavouriteFilm(filmId: Int){
      viewModelScope.launch(Dispatchers.IO) {
         filmRepository.deleteFavouriteFilm(filmId = filmId)
      }
   }

   fun getSearchedTitles(query: String): Flow<List<String>> {
      return flow {
         val titles = filmRepository.getSearchedTitles(query = query)
         emit(titles)
      }.flowOn(Dispatchers.IO)
   }

   fun getSearchedFilm(query: String) {
      viewModelScope.launch(Dispatchers.IO) {
         _searchedFilmFlow.value = filmRepository.getSearchedFilm(query = query).cachedIn(this).first()
      }
   }

   companion object{
      private const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
   }
}