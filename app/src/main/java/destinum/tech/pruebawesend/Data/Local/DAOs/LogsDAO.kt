package destinum.tech.pruebawesend.Data.Local.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import destinum.tech.pruebawesend.Data.Local.Entities.Logs
import io.reactivex.Single

@Dao
interface LogsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createLogs(logs: List<Logs>)

    @Query("select count(*) from logs")
    fun getLogsCount(): Single<Int>
}