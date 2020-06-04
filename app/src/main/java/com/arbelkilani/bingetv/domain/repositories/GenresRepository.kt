package com.arbelkilani.bingetv.domain.repositories

import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.genre.Genre

interface GenresRepository {
    suspend fun getGenres(): Resource<List<Genre>>
}