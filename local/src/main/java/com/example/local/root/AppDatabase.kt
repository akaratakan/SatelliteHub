package com.example.local.root

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.dao.SatelliteDao
import com.example.local.entity.SatelliteEntity

@Database(entities = [SatelliteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun satelliteDao(): SatelliteDao
}