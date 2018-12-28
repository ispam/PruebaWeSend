package destinum.tech.pruebawesend.Data.Local.Entities

import com.google.gson.annotations.SerializedName

data class DataResult(
    @SerializedName("data")
    val data: Data
)

data class Data(
    @SerializedName("ad_list")
    val ad_list: List<AdList>,
    @SerializedName("actions")
    val actions: Actions
)

data class Actions(
    @SerializedName("public_view")
    private val public_view: String
)