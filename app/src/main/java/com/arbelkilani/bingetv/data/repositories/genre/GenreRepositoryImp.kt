package com.arbelkilani.bingetv.data.repositories.genre

import com.arbelkilani.bingetv.data.entities.genre.GenreData
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val genreDao: GenreDao
) : GenresRepository {

    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth


    override suspend fun saveGenres() {
        apiTmdbService.getGenres().body()?.apply {
            genres.map { remote ->
                val localGenres = genreDao.getGenres()
                localGenres.map { local ->
                    if (remote.id == local.id)
                        remote.count = local.count
                }
                try {
                    fireStoreGenre(remote)
                    genreDao.saveGenre(remote)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun fireStoreGenre(genreData: GenreData) {
        auth.currentUser?.apply {
            fireStore.collection("users")
                .document(this.uid)
                .collection("genre_table")
                .document(genreData.id.toString())
                .set(genreData.mapOf())
        }
    }
}

