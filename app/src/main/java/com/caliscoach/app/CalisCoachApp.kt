package com.caliscoach.app

import android.app.Application
import androidx.room.Room
import com.caliscoach.app.data.db.AppDatabase

class CalisCoachApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "calis_coach_db").fallbackToDestructiveMigration().build()
    }
}
