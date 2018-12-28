package destinum.tech.pruebawesend.Data.Local.Entities

import com.google.gson.annotations.SerializedName

data class AdList(
    @SerializedName("data")
    val listData: ListData
)

data class ListData(
    @SerializedName("profile")
    val profile: Profile,
    @SerializedName("ad_id")
    val ad_id: Int,
    @SerializedName("temp_price")
    val temp_price: String,
    @SerializedName("temp_price_usd")
    val temp_price_usd: String
)
