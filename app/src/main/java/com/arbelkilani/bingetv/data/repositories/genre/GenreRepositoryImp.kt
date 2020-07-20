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
            genres.map { remote ->
                val localGenres = genreDao.getGenres()
                localGenres.map { local ->
                    if (remote.id == local.id)
                        remote.count = local.count
                }

                try {
                    genreDao.saveGenre(remote)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}