package destinum.tech.pruebawesend.Data.Local.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import destinum.tech.pruebawesend.Data.Local.Entities.Logs

@Dao
interface LogsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createLogs(logs: List<Logs>)
}