package com.arbelkilani.bingetv.data.source.remote.pagingsource

import androidx.paging.PagingSource
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import retrofit2.HttpException
import java.io.IOException

class AiringTodayPagingSource(
    private val service: ApiTmdbService
) : PagingSource<Int, TvShow>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.airingToday(position)
            val tvShows = response.results
            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (tvShows.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}