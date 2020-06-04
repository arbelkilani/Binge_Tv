package com.arbelkilani.bingetv.data.repositories.genre

import android.util.Log
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.ResponseHandler
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.ApiService
import com.arbelkilani.bingetv.domain.repositories.GenresRepository

class GenresRepositoryImp(
    private val apiService: ApiService,
    private val genreDao: GenreDao,
    private val responseHandler: ResponseHandler
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
        Log.i(TAG, "fetchLocal : " + genreDao.getGenres())
        return responseHandler.handleSuccess(200, genreDao.getGenres())
        //TODO check if return a code or use Status enum.
    }

    private suspend fun fetchRemote(): Resource<List<Genre>> {

        return try {
            val response = apiService.getGenres()

            if (response.isSuccessful && response.body() != null) {
                genreDao.saveGenres(response.body()!!.genres)
                responseHandler.handleSuccess(response.code(), response.body()!!.genres)
            } else {
                responseHandler.handleFailure(response.code(), response.message())
            }

        } catch (e: Exception) {
            Log.i(TAG, "exception {${e.printStackTrace()}}")
            responseHandler.handleFailure(-1, e.localizedMessage!!)
            //TODO define -1 error
        }
    }
}