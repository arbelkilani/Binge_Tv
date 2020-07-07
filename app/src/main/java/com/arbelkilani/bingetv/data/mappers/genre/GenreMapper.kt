package com.arbelkilani.bingetv.data.mappers.genre

import com.arbelkilani.bingetv.data.entities.genre.Genre
import com.arbelkilani.bingetv.data.entities.genre.GenreEntity
import com.arbelkilani.bingetv.data.mappers.base.Mapper

class GenreMapper :
    Mapper<GenreEntity, Genre> {

    override fun mapFromEntity(type: GenreEntity): Genre {
        return Genre(0, 0, type.test, 0)
    }

    override fun mapToEntity(type: Genre): GenreEntity {
        return GenreEntity(type.name)
    }

}