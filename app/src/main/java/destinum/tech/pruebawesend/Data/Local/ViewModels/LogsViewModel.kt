package destinum.tech.pruebawesend.Data.Local.ViewModels

import destinum.tech.pruebawesend.Data.Local.Entities.Logs
import destinum.tech.pruebawesend.Data.Local.TestDB
import io.reactivex.Completable
import javax.inject.Inject

class LogsViewModel @Inject constructor(val db: TestDB) {

    fun insertLogs(logs: List<Logs>): Completable = Completable.fromAction { db.logsDAO().createLogs(logs)}
}