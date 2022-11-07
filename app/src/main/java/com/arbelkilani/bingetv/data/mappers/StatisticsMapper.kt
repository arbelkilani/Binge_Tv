package com.arbelkilani.bingetv.data.mappers

import com.arbelkilani.bingetv.data.entities.profile.StatisticsData
import com.arbelkilani.bingetv.data.mappers.base.Mapper
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import java.util.concurrent.TimeUnit

class StatisticsMapper : Mapper<StatisticsEntity, StatisticsData> {

    override fun mapFromEntity(type: StatisticsEntity): StatisticsData {

        return StatisticsData(
            episodeCount = type.episodeCount,
            tvShowCount = type.tvShowCount,
            returningTvShowCount = type.returningTvShowCount,
            totalSpentTime = type.totalSpentTime
        )
    }

    override fun mapToEntity(type: StatisticsData): StatisticsEntity {

        return StatisticsEntity(
            episodeCount = type.episodeCount,
            tvShowCount = type.tvShowCount,
            returningTvShowCount = type.returningTvShowCount,
            totalSpentTime = type.totalSpentTime,
            minutes = type.totalSpentTime % 60,
            hours = (type.totalSpentTime / 60) % 24,
            days = TimeUnit.MINUTES.toDays(type.totalSpentTime.toLong()).toInt() % 30,
            months = ((TimeUnit.MINUTES.toDays(type.totalSpentTime.toLong()).toInt()) / 30) % 12,
            years = (TimeUnit.MINUTES.toDays(type.totalSpentTime.toLong()).toInt()) / 30 / 12
        )
    }
}