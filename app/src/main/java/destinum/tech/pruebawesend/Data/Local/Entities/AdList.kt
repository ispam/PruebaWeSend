package destinum.tech.pruebawesend.Data.Local.Entities

import com.google.gson.annotations.SerializedName

class AdList {
    @SerializedName("data")
     val data: AdData ?= null

    @SerializedName("actions")
    val actions: Actions ?= null

    data class Actions(
        @SerializedName("public_view")
        private val public_view: String
    )
}

