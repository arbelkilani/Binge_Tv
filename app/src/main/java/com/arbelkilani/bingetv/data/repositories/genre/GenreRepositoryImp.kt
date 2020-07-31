package com.arbelkilani.bingetv.data.repositories.genre

import com.arbelkilani.bingetv.BingeTvApp
import com.arbelkilani.bingetv.data.entities.genre.GenreData
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.remote.apiservice.ApiTmdbService
import com.arbelkilani.bingetv.domain.repositories.GenresRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GenreRepositoryImp(
    private val apiTmdbService: ApiTmdbService,
    private val genreDao: GenreDao
) : GenresRepository {

    val firebaseFireStore = Firebase.firestore
    val firebaseAuth = Firebase.auth

    override suspend fun saveGenres() {
        apiTmdbService.getGenres().body()?.apply {
            genres.map { remote ->
                val localGenres = genreDao.getGenres()
                localGenres.map { local ->
                    if (remote.id == local.id)
                        remote.count = local.count
                }
                try {
                    //saveGenre(remote)
                    genreDao.saveGenre(remote)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun saveGenre(genre: GenreData) {
        val account = GoogleSignIn.getLastSignedInAccount(BingeTvApp.instance)
        account?.apply {
            firebaseFireStore.collection("users")
                .document(this.idToken!!)
                .collection("genres")
                .add(genre)

        }

    }
}