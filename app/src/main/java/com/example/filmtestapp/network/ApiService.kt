package com.example.filmtestapp.network

import com.example.filmtestapp.data.FilmResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

   @GET("movie/now_playing")
   suspend fun fetchCurrentFilms(
      @Query("page") page: Int,
      @Query("api_key") apiKey: String = API_KEY
   ): FilmResponse

   @GET("search/movie")
   suspend fun searchMovie(
      @Query("query") query: String,
      @Query("api_key") apiKey: String = API_KEY
   ): FilmResponse?

   companion object {
      private const val API_KEY = "996a0312ed8eb5f6db790c5662fcc65a"
   }
}