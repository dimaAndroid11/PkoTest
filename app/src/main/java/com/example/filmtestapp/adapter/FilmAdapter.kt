package com.example.filmtestapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.filmtestapp.R
import com.example.filmtestapp.data.FilmItem
import com.example.filmtestapp.databinding.ItemFilmBinding

class FilmAdapter(private val onClickListener: OnClickListener, ): PagingDataAdapter<FilmItem, FilmAdapter.FilmViewHolder>(
   diffCallback = DifUtilItemCallBack()
){

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
         FilmViewHolder(
            binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickListener = onClickListener
         )

   override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
      getItem(position)?.let { holder.bind(it) }
   }

   inner class FilmViewHolder(
      private val binding: ItemFilmBinding,
      private val onClickListener: OnClickListener
   ): RecyclerView.ViewHolder(binding.root) {

      private var test = false

      fun bind(film: FilmItem) {
         onClickListener.isFavouriteFilm(filmId = film.id){ isFavourite ->
            test = isFavourite
            if (isFavourite) {
               binding.favouriteIcon.setImageResource(R.drawable.ic_star_24)
            }
         }
         binding.filmPoster.load(film.previewPoster)
         binding.itemContainer.setOnClickListener{

            onClickListener.onFilmClick(
               title = film.title,
               date =  film.date,
               rate = film.rate,
               image = film.mainPoster,
               description = film.description
            )
         }

         clickOnFavouriteIcon(film)
      }

      private fun clickOnFavouriteIcon(film: FilmItem) {
         binding.favouriteIcon.setOnClickListener {
            test = !test
            if(test){
               binding.favouriteIcon.setImageResource(R.drawable.ic_star_24)
               onClickListener.addFavouriteFilm(filmItem = film)
            }else {
               binding.favouriteIcon.setImageResource(R.drawable.ic_star_outline_24)
               onClickListener.deleteFavouriteFilm(filmId = film.id)
            }
         }
      }
   }
}

class DifUtilItemCallBack : DiffUtil.ItemCallback<FilmItem>() {
   override fun areItemsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
      return oldItem == newItem
   }

   override fun areContentsTheSame(oldItem: FilmItem, newItem: FilmItem): Boolean {
      return oldItem.id == newItem.id && oldItem.title == newItem.title
              && oldItem.date == newItem.date && oldItem.previewPoster == newItem.previewPoster
              && oldItem.rate == newItem.rate && oldItem.description == newItem.description
   }
}