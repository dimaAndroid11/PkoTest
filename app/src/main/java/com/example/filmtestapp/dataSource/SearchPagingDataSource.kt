package com.example.filmtestapp.dataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.filmtestapp.data.FilmItem
import com.example.filmtestapp.network.ApiService
import com.example.filmtestapp.utils.mapToFilmItem

class SearchPagingDataSource(
   private val query: String,
   private val apiService: ApiService
): PagingSource<Int, FilmItem>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmItem> {
      val page: Int = params.key ?: 1
      val pageSize = params.loadSize

      return try {
         val response = apiService.searchMovie(query)?.results?.mapToFilmItem() ?: emptyList()
         val nextKey = if(response.size < pageSize) null else page + 1
         val prevKey = if(page == 1) null else page - 1
         LoadResult.Page(response,prevKey,nextKey)
      }catch (e: Exception) {
         LoadResult.Error(e)
      }
   }

   override fun getRefreshKey(state: PagingState<Int, FilmItem>): Int? {
      val anchorPosition = state.anchorPosition ?: return null
      val page = state.closestPageToPosition(anchorPosition) ?: return null
      return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
   }
}