package destinum.tech.pruebawesend.Data.Local.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import io.reactivex.Single

@Dao
interface ListDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createListData(listData: List<ListData>)

    @Query("select * from listdata where logs_id = :log_id")
    fun getData(log_id: Long): Single<List<ListData>>
}