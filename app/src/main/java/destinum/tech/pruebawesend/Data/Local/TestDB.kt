package destinum.tech.pruebawesend.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import destinum.tech.pruebawesend.Data.Local.DAOs.ListDataDAO
import destinum.tech.pruebawesend.Data.Local.DAOs.LogsDAO
import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.Data.Local.Entities.Logs

@Database(entities = arrayOf(Logs::class, ListData::class), version = 1, exportSchema = false)
abstract class TestDB: RoomDatabase() {

    abstract fun logsDAO(): LogsDAO

    abstract fun listDataDAO(): ListDataDAO
}