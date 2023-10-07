package com.example.filmtestapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.filmtestapp.adapter.FilmAdapter
import com.example.filmtestapp.adapter.OnClickListener
import com.example.filmtestapp.data.FilmItem
import com.example.filmtestapp.databinding.FilmFragmentBinding
import com.example.filmtestapp.utils.Resource
import com.example.filmtestapp.viewModel.FilmViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmFragment: Fragment(), OnClickListener {

   private var _binding: FilmFragmentBinding? = null
   private val binding: FilmFragmentBinding get() = checkNotNull(_binding)

   private val viewModel: FilmViewModel by viewModel()
   private val filmAdapter = FilmAdapter(this)

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = FilmFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.listFilms.adapter = filmAdapter

      viewLifecycleOwner.lifecycleScope.launch {
          repeatOnLifecycle(Lifecycle.State.STARTED) {
             val autoText = binding.searchView.text.toString()
             if (autoText.isNotEmpty()){
                viewModel.getSearchedFilm(autoText)
             }else{
                viewModel.getCurrentFilmList()
                fetchCurrentList()
             }
          }
      }

      searchMovie()
      onRefreshListener()
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

   override fun onFilmClick(
      title: String,
      date: String,
      rate: Double,
      image: String,
      description: String
   ) {
      val bundle = Bundle()
      with(bundle){
         putString(TITLE_TAG, title)
         putString(DATE_TAG, date)
         putDouble(RATE_TAG, rate)
         putString(IMAGE_TAG, image)
         putString(DESCRIPTION_TAG, description)
      }

      val detailsFragment = DetailsFragment()
      detailsFragment.arguments = bundle

      activity?.supportFragmentManager?.beginTransaction()
         ?.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
         )
         ?.replace(R.id.fragment_container, detailsFragment)
         ?.addToBackStack(null)
         ?.commit()
   }

   override fun isFavouriteFilm(filmId: Int, callBack: (Boolean) -> Unit){
      viewLifecycleOwner.lifecycleScope.launch {
         repeatOnLifecycle(Lifecycle.State.STARTED) {
            callBack(viewModel.isFilmFavourite(filmId).first())
         }
      }
   }

   override fun addFavouriteFilm(filmItem: FilmItem) {
      viewModel.addFavouriteFilm(filmItem = filmItem)
   }

   override fun deleteFavouriteFilm(filmId: Int) {
     viewModel.deleteFavouriteFilm(filmId = filmId)
   }

   private fun onRefreshListener(){
      binding.swipeLayout.setOnRefreshListener {
         viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
               fetchCurrentList()
            }
         }
         binding.swipeLayout.isRefreshing = false
         binding.searchView.setText("")
      }
   }

   private suspend fun fetchCurrentList(){
      viewModel.filmFlow.collect { resource ->
         when(resource){
            is Resource.Loading -> {}
            is Resource.Error -> { showAlertDialog(resource.message) }
            is Resource.Empty -> Unit
            is Resource.Success -> {
               filmAdapter.submitData(resource.data!!)
            }
         }
      }
   }

   private fun showAlertDialog(message: String?) = AlertDialog.Builder(requireContext())
         .setTitle(getString(R.string.dialog_title))
         .setMessage(message ?: getString(R.string.default_error_message))
         .create().show()

   private fun searchMovie() {
      binding.searchView.addTextChangedListener(
         onTextChanged = { charSequence, _, _, _ ->
            val text = charSequence.toString()
            viewModel.getSearchedFilm(text)
            viewLifecycleOwner.lifecycleScope.launch {
               repeatOnLifecycle(Lifecycle.State.STARTED) {
                  viewModel.searchedFilm.collect{ films ->
                     val list = viewModel.getSearchedTitles(text).first()
                     val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, list )
                     binding.searchView.setAdapter(adapter)
                     binding.searchView.setOnItemClickListener { parent, view, position, id ->
                        lifecycleScope.launch { filmAdapter.submitData(films) }
                     }
                  }
               }
            }
      })
   }

   companion object {
      const val TITLE_TAG = "TITLE_TAG"
      const val DATE_TAG = "DATE_TAG"
      const val RATE_TAG = "RATE_TAG"
      const val IMAGE_TAG = "IMAGE_TAG"
      const val DESCRIPTION_TAG = "DESCRIPTION_TAG"
   }
}