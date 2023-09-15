package com.example.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.local.entity.SatelliteEntity

@Dao
interface SatelliteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(satellite: SatelliteEntity)

    @Query("SELECT * FROM satellite_db WHERE id = :id")
    fun fetchWithId(id: Int): SatelliteEntity?

    @Query("SELECT * FROM satellite_db")
    fun fetchAll(): List<SatelliteEntity>?

    @Delete
    fun delete(satellite: SatelliteEntity)

}