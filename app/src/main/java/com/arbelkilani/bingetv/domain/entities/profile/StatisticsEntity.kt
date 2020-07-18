package com.arbelkilani.bingetv.domain.entities.profile

data class StatisticsEntity(
    var episodeCount: Int = 0,
    var tvShowCount: Int = 0,
    var totalSpentTime: Int = 0,
    var minutes: Int = 0,
    var hours: Int = 0,
    var days: Int = 0,
    var months: Int = 0,
    var years: Int = 0
)