package com.example.filmtestapp

import android.app.Application
import com.example.filmtestapp.module.dataBaseModule
import com.example.filmtestapp.module.repositoryModule
import com.example.filmtestapp.module.retrofitModule
import com.example.filmtestapp.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

   override fun onCreate() {
      super.onCreate()
      startKoin {
         androidContext(this@MainApplication)
         modules(listOf(
            retrofitModule,
            repositoryModule,
            viewModelModule,
            dataBaseModule
         ))
      }
   }
}