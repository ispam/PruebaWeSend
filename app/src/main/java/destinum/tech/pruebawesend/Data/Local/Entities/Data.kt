package destinum.tech.pruebawesend.Data.Local.Entities

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("ad_list")
    var movies: List<AdList>
)