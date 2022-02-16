package com.example.ejerciciobrubank.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ejerciciobrubank.data.model.Movie

/**
 * @author Axel Sanchez
 */
@Database(
    entities = [Movie::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class Database: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}