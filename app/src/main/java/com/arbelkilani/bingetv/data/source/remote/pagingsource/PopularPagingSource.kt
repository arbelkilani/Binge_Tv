package com.arbelkilani.bingetv.data.source.remote.pagingsource

import androidx.paging.PagingSource
import com.arbelkilani.bingetv.data.mappers.tv.TvShowMapper
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import retrofit2.HttpException
import java.io.IOException

class PopularPagingSource(
    private val service: ApiTmdbService,
    private val tvShowDao: TvDao
) : PagingSource<Int, TvShowEntity>() {

    private val tvShowMapper = TvShowMapper()

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.popular(position)
            val tvShows = response.results.map {
                val local = tvShowDao.getTvShow(it.id)
                local?.let { item ->
                    it.watched = item.watched
                    it.watchlist = item.watchlist
                }
                tvShowMapper.mapToEntity(it)
            }
            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (tvShows.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}