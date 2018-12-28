package destinum.tech.pruebawesend.Data.Local.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import destinum.tech.pruebawesend.Data.Local.Entities.ListData

@Dao
interface ListDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createListData(listData: List<ListData>)
}