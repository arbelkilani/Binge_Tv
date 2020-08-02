package com.arbelkilani.bingetv.data.mappers.tv

import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeDocument
import com.arbelkilani.bingetv.data.mappers.base.DocumentDataMapper

class NextEpisodeDocumentDataMapper : DocumentDataMapper<NextEpisodeDocument, NextEpisodeData> {
    override fun mapFromData(type: NextEpisodeData): NextEpisodeDocument {
        return NextEpisodeDocument(

        )
    }

    override fun mapFromDocument(type: NextEpisodeDocument): NextEpisodeData {
        return NextEpisodeData(
            name = type.name,
            number = type.number.toInt(),
            season = type.season.toInt(),
            summary = type.summary,
            time = type.time.toLong(),
            tv_next_episode = type.tv_next_episode.toInt()
        )
    }
}