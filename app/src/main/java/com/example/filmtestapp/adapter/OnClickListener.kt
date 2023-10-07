package com.example.filmtestapp.adapter

import com.example.filmtestapp.data.FilmItem

interface OnClickListener {

   fun onFilmClick(title: String, date: String, rate: Double, image:String, description: String)

   fun addFavouriteFilm(filmItem: FilmItem)

   fun deleteFavouriteFilm(filmId: Int)

   fun isFavouriteFilm(filmId: Int, callBack: (Boolean) -> Unit)

}