package com.arbelkilani.bingetv.data.repositories.profile

import android.content.Intent
import android.util.Log
import com.arbelkilani.bingetv.BingeTvApp
import com.arbelkilani.bingetv.data.entities.profile.StatisticsData
import com.arbelkilani.bingetv.data.mappers.StatisticsMapper
import com.arbelkilani.bingetv.data.mappers.genre.GenreMapper
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import com.arbelkilani.bingetv.domain.repositories.ProfileRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileRepositoryImp(
    private val tvDao: TvDao,
    private val genreDao: GenreDao
) : ProfileRepository {

    companion object {
        private const val TAG = "ProfileRepository"
    }

    private val statisticsMapper = StatisticsMapper()
    private val genreMapper = GenreMapper()

    private var firebaseAuth = Firebase.auth
    private var firebaseFireStore = Firebase.firestore

    override suspend fun getStatistics(): StatisticsEntity {
        var episodeCount = 0
        var tvShowCount = 0
        var returningTvShowCount = 0
        var totalSpentTime = 0
        val tvShows = tvDao.getAllTvShows()

        tvShows?.apply {
            map {
                episodeCount += it.watchedCount
                if (it.watchedCount > 0) {
                    tvShowCount += 1
                    totalSpentTime += (it.runtime * it.watchedCount)
                    if (it.inProduction)
                        returningTvShowCount += 1
                }
            }
        }

        val statisticsData =
            StatisticsData(episodeCount, tvShowCount, returningTvShowCount, totalSpentTime)
        return statisticsMapper.mapToEntity(statisticsData)
    }

    override suspend fun getGenres(): List<GenreEntity> {
        return genreDao.getGenres().let { genresData ->
            val sum = genreDao.sum()
            if (sum == 0)
                return emptyList()
            else {
                val list = genresData.map { genreData ->
                    genreData.percentage = genreData.count.toFloat() * 100 / sum
                    genreMapper.mapToEntity(genreData)
                }

                var othersPercentage = 0f
                var othersCount = 0
                list.filter { it.percentage < 15 }
                    .map {
                        othersPercentage += it.percentage
                        othersCount += it.count
                    }

                val mutable = list.filter { it.percentage >= 15 }.toMutableList()
                mutable.add(GenreEntity(-1, "Others", othersCount, othersPercentage))

                mutable
            }
        }
    }

    override fun isConnected(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(BingeTvApp.instance) != null
    }

    override fun getSignedInAccountFromIntent(data: Intent?): FirebaseUser? {
        var user: FirebaseUser? = null
        data?.let {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.apply {
                    user = firebaseAuthWithGoogle(idToken)
                }
            } catch (e: ApiException) {
                Log.e(TAG, "Exception : ${e.localizedMessage}")
            }
        }

        return user
    }

    private fun firebaseAuthWithGoogle(idToken: String?): FirebaseUser? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        var user: FirebaseUser? = null
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = firebaseAuth.currentUser
                    fireStoreUser(user)
                } else {
                    Log.e(TAG, "Exception : ${task.exception}")
                }
            }
        return user
    }

    private fun fireStoreUser(user: FirebaseUser?) {
        user?.apply {
            val map = mapOf(
                "uid" to this.uid,
                "email" to this.email,
                "displayName" to this.displayName
            )
            firebaseFireStore.collection("users")
                .document(this.uid)
                .set(map)
        }
    }
}