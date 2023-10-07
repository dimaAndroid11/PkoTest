package com.example.filmtestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.filmtestapp.FilmFragment.Companion.DATE_TAG
import com.example.filmtestapp.FilmFragment.Companion.DESCRIPTION_TAG
import com.example.filmtestapp.FilmFragment.Companion.IMAGE_TAG
import com.example.filmtestapp.FilmFragment.Companion.RATE_TAG
import com.example.filmtestapp.FilmFragment.Companion.TITLE_TAG
import com.example.filmtestapp.databinding.DetailsFragmentBinding

class DetailsFragment: Fragment() {

   private var _binding: DetailsFragmentBinding? = null
   private val binding: DetailsFragmentBinding get() = checkNotNull(_binding)

   private var title: String? = null
   private var date: String? = null
   private var rate: Double? = null
   private var image: String? = null
   private var description: String? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      arguments?.let { film ->
         title = film.getString(TITLE_TAG)
         date = film.getString(DATE_TAG)
         rate = film.getDouble(RATE_TAG)
         image = film.getString(IMAGE_TAG)
         description = film.getString(DESCRIPTION_TAG)
      }
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = DetailsFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(
      view: View,
      savedInstanceState: Bundle?
   ) {
      super.onViewCreated(view, savedInstanceState)
      setFilmDetails()

      binding.backButton.setOnClickListener {
         parentFragmentManager.popBackStack()
      }
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

   private fun setFilmDetails(){
      with(binding) {
         val mainImage = image
         filmPoster.load(mainImage)
         filmTitle.text = title
         filmDate.text = date
         ratingBar.rating = rate?.toFloat()?.div(2) ?: 0F
         filmDescription.text = description
      }
   }
}