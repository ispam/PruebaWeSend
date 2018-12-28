package destinum.tech.pruebawesend.Data.Local.ViewModels

import destinum.tech.pruebawesend.Data.Local.Entities.Log
import destinum.tech.pruebawesend.Data.Local.TestDB
import io.reactivex.Completable
import javax.inject.Inject

class LogViewModel @Inject constructor(private val db: TestDB) {

    fun insertLog(logs: Log): Completable = Completable.fromAction { db.logsDAO().createLog(logs)}

    fun getCurrentLogsCount() = db.logsDAO().getLogsCount()
    fun getCurrentLogsCount2() = db.logsDAO().getLogsTry()

    fun getAllLogs() = db.logsDAO().getLogs()
}