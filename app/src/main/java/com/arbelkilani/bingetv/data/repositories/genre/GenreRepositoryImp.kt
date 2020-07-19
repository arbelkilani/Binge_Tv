package com.arbelkilani.bingetv.data.repositories.genre

import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.domain.repositories.GenresRepository

class GenreRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val genreDao: GenreDao
) : GenresRepository {

    override suspend fun saveGenres() {
        apiTmdbService.getGenres().body()?.apply {
            genres.map { genreData ->
                try {
                    genreDao.saveGenre(genreData)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}