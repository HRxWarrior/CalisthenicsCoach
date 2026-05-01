package com.caliscoach.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val exerciseType: String,
    val reps: Int = 0,
    val durationSeconds: Int = 0,
    val formScore: Float = 0f,
    val date: String,
    val timestamp: Long = System.currentTimeMillis()
)
