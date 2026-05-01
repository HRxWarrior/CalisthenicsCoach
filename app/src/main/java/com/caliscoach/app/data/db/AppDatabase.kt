package com.caliscoach.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.caliscoach.app.data.db.dao.DailyStatsDao
import com.caliscoach.app.data.db.dao.WorkoutDao
import com.caliscoach.app.data.db.entity.DailyStatsEntity
import com.caliscoach.app.data.db.entity.WorkoutEntity

@Database(entities = [WorkoutEntity::class, DailyStatsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun dailyStatsDao(): DailyStatsDao
}
