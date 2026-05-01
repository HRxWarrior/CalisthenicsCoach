package com.caliscoach.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_stats")
data class DailyStatsEntity(
    @PrimaryKey val date: String,
    val totalPushups: Int = 0,
    val totalSquats: Int = 0,
    val totalPlankSeconds: Int = 0,
    val totalWorkouts: Int = 0,
    val streak: Int = 0
)
