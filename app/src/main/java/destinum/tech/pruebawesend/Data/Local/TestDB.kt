package destinum.tech.pruebawesend.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import destinum.tech.pruebawesend.Data.Local.DAOs.ListDataDAO
import destinum.tech.pruebawesend.Data.Local.DAOs.LogDAO
import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.Data.Local.Entities.Log

@Database(entities = arrayOf(Log::class, ListData::class), version = 1, exportSchema = false)
abstract class TestDB: RoomDatabase() {

    abstract fun logsDAO(): LogDAO

    abstract fun listDataDAO(): ListDataDAO
}