package destinum.tech.pruebawesend.Data.Local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Log(
    var date: String,
    var rate_usd: String,
    var rate_ves: String,
    var above: String,
    var below: String
) {
    @PrimaryKey(autoGenerate = true)
    var logs_id: Long  = 0
}

