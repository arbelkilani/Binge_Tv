package com.arbelkilani.bingetv.data.repositories.profile

import com.arbelkilani.bingetv.data.source.local.tv.TvDao
import com.arbelkilani.bingetv.domain.repositories.ProfileRepository

class ProfileRepositoryImp(
    private val tvDao: TvDao
) : ProfileRepository {

    override suspend fun getWatchedEpisodesCount(): Int {
        return 99
    }
}