package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.genre.Genre

interface GenresRepository {
    suspend fun getGenres(): Resource<List<Genre>>
}