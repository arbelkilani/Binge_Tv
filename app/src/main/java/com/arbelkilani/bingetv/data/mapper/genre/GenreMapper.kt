package com.arbelkilani.bingetv.data.mapper.genre

import com.arbelkilani.bingetv.data.mapper.base.Mapper
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.genre.GenreEntity

class GenreMapper :
    Mapper<GenreEntity, Genre> {

    override fun mapFromEntity(type: GenreEntity): Genre {
        return Genre(0, 0, type.test, 0)
    }

    override fun mapToEntity(type: Genre): GenreEntity {
        return GenreEntity(type.name)
    }

}