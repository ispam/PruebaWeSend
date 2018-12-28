package destinum.tech.pruebawesend.Data.Local.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import destinum.tech.pruebawesend.Data.Local.Entities.Log
import io.reactivex.Single

@Dao
interface LogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createLog(logs: Log)

    @Query("select count(*) from log")
    fun getLogsCount(): Single<Int>

    @Query("select * from log")
    fun getLogs(): Single<List<Log>>
}