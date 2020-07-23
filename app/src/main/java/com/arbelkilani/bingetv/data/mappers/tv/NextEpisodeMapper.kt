package com.arbelkilani.bingetv.data.mappers.tv

import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.domain.entities.tv.NextEpisodeEntity

class NextEpisodeMapper : Mapper<NextEpisodeEntity, NextEpisodeData> {

    override fun mapFromEntity(type: NextEpisodeEntity?): NextEpisodeData {
        if (type == null)
            return NextEpisodeData()

        return NextEpisodeData(
            name = type.name,
            season = type.season,
            number = type.number,
            summary = type.summary,
            time = type.time
        )
    }

    override fun mapToEntity(type: NextEpisodeData?): NextEpisodeEntity {
        if (type == null)
            return NextEpisodeEntity()

        return NextEpisodeEntity(
            name = type.name,
            season = type.season,
            number = type.number,
            summary = type.summary,
            time = type.time
        )
    }
}