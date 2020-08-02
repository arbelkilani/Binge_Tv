package com.arbelkilani.bingetv.data.repositories.profile

import android.content.Intent
import com.arbelkilani.bingetv.BingeTvApp
import com.arbelkilani.bingetv.data.entities.genre.GenreData
import com.arbelkilani.bingetv.data.entities.profile.StatisticsData
import com.arbelkilani.bingetv.data.entities.season.SeasonData
import com.arbelkilani.bingetv.data.entities.tv.TvShowData
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.mappers.StatisticsMapper
import com.arbelkilani.bingetv.data.mappers.genre.GenreMapper
import com.arbelkilani.bingetv.data.source.local.genre.GenreDao
import com.arbelkilani.bingetv.data.source.local.season.SeasonDao
import com.arbelkilani.bingetv.data.source.local.tv.NextEpisodeDao
import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import com.arbelkilani.bingetv.domain.repositories.ProfileRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileRepositoryImp(
    private val tvDao: TvDao,
    private val genreDao: GenreDao,
    private val seasonDao: SeasonDao,
    private val nextEpisodeDao: NextEpisodeDao
) : ProfileRepository {

    companion object {
        private const val TAG = "ProfileRepository"
    }

    private val statisticsMapper = StatisticsMapper()
    private val genreMapper = GenreMapper()

    private val fireStore = Firebase.firestore
    private val auth = Firebase.auth

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

    override fun getSignedInAccountFromIntent(data: Intent?): AuthCredential? {
        if (data == null)
            return null
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java) ?: return null
        account.apply {
            return GoogleAuthProvider.getCredential(idToken, null)
        }
    }

    override suspend fun saveUser(user: FirebaseUser?) {
        user?.apply {
            val map = mapOf(
                "uid" to this.uid,
                "email" to this.email,
                "displayName" to this.displayName
            )
            fireStore.collection("users")
                .document(this.uid)
                .set(map)

            synchroniseDataBase()
        }
    }

    private suspend fun synchroniseDataBase() {
        val tvShows = tvDao.getAllTvShows()
        tvShows?.map {
            fireStoreTvShow(it)
        }

        val seasons = seasonDao.getAllSeasons()
        seasons?.map {
            fireStoreSeason(it)
        }

        val nextEpisodes = nextEpisodeDao.getAllNextEpisodes()
        nextEpisodes?.map {
            fireStoreNextEpisode(it)
        }

        val genres = genreDao.getAllGenres()
        genres?.map {
            fireStoreGenre(it)
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

    private fun fireStoreNextEpisode(nextEpisodeData: NextEpisodeData) {
        auth.currentUser?.apply {
            fireStore.collection("users")
                .document(this.uid)
                .collection("next_episode_table")
                .document(nextEpisodeData.tv_next_episode.toString())
                .set(nextEpisodeData.mapOf())
        }
    }

    private fun fireStoreSeason(seasonData: SeasonData) {
        auth.currentUser?.apply {
            fireStore.collection("users")
                .document(this.uid)
                .collection("season_table")
                .document(seasonData.id.toString())
                .set(seasonData.mapOf())
        }
    }

    private fun fireStoreTvShow(tvShowData: TvShowData) {
        auth.currentUser?.apply {
            fireStore.collection("users")
                .document(this.uid)
                .collection("tv_table")
                .document(tvShowData.id.toString())
                .set(tvShowData.mapOf())
        }
    }
}