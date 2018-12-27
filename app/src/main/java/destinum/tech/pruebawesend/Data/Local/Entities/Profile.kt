package destinum.tech.pruebawesend.Data.Local.Entities

import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("username")
    val username: String,
    @SerializedName("feedback_score")
    val feedback_score: Int,
    @SerializedName("trade_count")
    val trade_count: String,
    @SerializedName("last_online")
    val last_online: String,
    @SerializedName("name")
    val name: String
)