package destinum.tech.pruebawesend.Data.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Logs(
    var date: String,
    var rate: String,
    var above: String,
    var below: String
) {
    @PrimaryKey(autoGenerate = false)
    var logs_id: Long  = 0
}

