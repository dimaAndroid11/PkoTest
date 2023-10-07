package com.example.filmtestapp.module

import com.example.filmtestapp.database.FilmDataBase
import com.example.filmtestapp.database.FilmDatabaseConstructor
import com.example.filmtestapp.network.ApiService
import com.example.filmtestapp.repository.FilmRepository
import com.example.filmtestapp.repository.FilmRepositoryImpl
import com.example.filmtestapp.viewModel.FilmViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

private  val loggingInterceptor = HttpLoggingInterceptor().apply {
   level = HttpLoggingInterceptor.Level.BODY
}

val retrofitModule = module { single {
   Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
      .build().create(ApiService::class.java)
   }
}

val repositoryModule = module {
   single<FilmRepository> { FilmRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
   viewModel { FilmViewModel(get()) }
}

val dataBaseModule = module {
   single { FilmDatabaseConstructor.create(get()) }
   factory { get<FilmDataBase>().filmDao() }
}


