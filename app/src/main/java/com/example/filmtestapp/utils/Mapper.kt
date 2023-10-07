package com.example.filmtestapp.utils

import com.example.filmtestapp.data.FilmItem
import com.example.filmtestapp.data.Result
import com.example.filmtestapp.database.FilmDto

private const val IMAGE_PREFIX = "https://image.tmdb.org/t/p/original"

internal fun List<Result>.mapToFilmItem(): List<FilmItem> =
   this.map { filmResponse -> FilmItem(
         id = filmResponse.id,
         title = filmResponse.title,
         date = filmResponse.release_date,
         rate = filmResponse.vote_average,
         previewPoster = IMAGE_PREFIX + filmResponse.backdrop_path,
         mainPoster = IMAGE_PREFIX + filmResponse.poster_path,
         description = filmResponse.overview
      )
   }

internal fun FilmItem.mapToFilmDto(): FilmDto =
    FilmDto(itemId = this.id)
