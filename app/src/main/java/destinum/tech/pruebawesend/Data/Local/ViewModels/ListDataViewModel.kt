package destinum.tech.pruebawesend.Data.Local.ViewModels

import destinum.tech.pruebawesend.Data.Local.Entities.ListData
import destinum.tech.pruebawesend.Data.Local.TestDB
import io.reactivex.Completable
import javax.inject.Inject

class ListDataViewModel @Inject constructor(private val db: TestDB) {

    fun insertListData(listData: List<ListData>): Completable = Completable.fromAction { db.listDataDAO().createListData(listData) }

    fun getListDataBasedOnLogID(log_id: Long) = db.listDataDAO().getData(log_id)
}