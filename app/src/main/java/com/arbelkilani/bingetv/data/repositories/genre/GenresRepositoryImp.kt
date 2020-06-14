package com.arbelkilani.bingetv.data.repositories.genre

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.GenresRepository

class GenresRepositoryImp(
    private val apiService: ApiService,
    private val genreDao: GenreDao
) :
    GenresRepository {

    private val TAG = GenresRepositoryImp::class.java.simpleName

    override suspend fun getGenres(): Resource<List<Genre>> {
        return when {
            genreDao.isEmpty() -> fetchRemote()
            else -> fetchLocal()
        }
    }

    private suspend fun fetchLocal(): Resource<List<Genre>> {
        Log.i(TAG, "fetchLocal()")
        return Resource.success(genreDao.getGenres())
    }

    private fun fetchRemote(): Resource<List<Genre>> {
        Log.i(TAG, "fetchRemote()")
        return Resource.exception(Exception(), null)
        /*return try {
            val response = apiService.getGenres()
            return if (response.isSuccessful && response.body() != null) {
                genreDao.saveGenres(response.body()!!.genres)
                Resource.success(response.body()!!.genres, response.code())
            } else {
                Log.i(TAG, "fetchRemote() error : ${response.code()}")
                Resource.error(null, response.code())
            }

        } catch (e: Exception) {
            Log.i(TAG, "fetchRemote() exception : ${e.localizedMessage}")
            Resource.error(null, -1)
        }*/
    }
}