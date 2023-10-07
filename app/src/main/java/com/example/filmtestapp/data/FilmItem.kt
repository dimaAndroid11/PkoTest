package com.example.filmtestapp.data

data class FilmItem(
   val id: Int,
   val title: String,
   val date: String,
   val rate: Double,
   val previewPoster: String,
   val mainPoster: String,
   val description: String,
   var isFavourite: Boolean = false
)
