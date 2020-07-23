package com.arbelkilani.bingetv.data.mappers.genre

import com.arbelkilani.bingetv.data.entities.genre.GenreData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity

class GenreMapper : Mapper<GenreEntity, GenreData> {

    override fun mapFromEntity(type: GenreEntity?): GenreData {

        if (type == null)
            return GenreData()

        return GenreData(
            id = type.id,
            name = type.name,
            count = type.count,
            percentage = type.percentage
        )
    }

    override fun mapToEntity(type: GenreData?): GenreEntity {

        if (type == null)
            return GenreEntity()

        return GenreEntity(
            id = type.id,
            name = type.name,
            count = type.count,
            percentage = type.percentage
        )
    }
}