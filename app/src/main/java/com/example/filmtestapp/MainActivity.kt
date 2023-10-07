package com.example.filmtestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmtestapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

   private var _binding: MainActivityBinding? = null
   private val binding: MainActivityBinding get() = checkNotNull(_binding)

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _binding = MainActivityBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val filmFragment = FilmFragment()

      if(savedInstanceState == null) {
         supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(binding.fragmentContainer.id, filmFragment)
            .addToBackStack(null).commit()
      }
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}

