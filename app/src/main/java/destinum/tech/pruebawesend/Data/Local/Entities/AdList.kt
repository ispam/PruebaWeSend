package destinum.tech.pruebawesend.Data.Local.Entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class AdList(
    @SerializedName("data")
    val listData: ListData
)

@Entity
data class ListData(
    @SerializedName("profile")
    @Embedded
    var profile: Profile,
    @SerializedName("ad_id")
    var ad_id: Int,
    @SerializedName("temp_price")
    var temp_price: String,
    @SerializedName("temp_price_usd")
    var temp_price_usd: String,
    var logs_id: Long
) {
    @PrimaryKey(autoGenerate = true)
    var list_data_id: Long = 0
}
