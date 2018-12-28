package destinum.tech.pruebawesend.Data.Remote

import destinum.tech.pruebawesend.Data.Local.Entities.DataResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LocalBitcoinsAPI {

    @GET("buy-bitcoins-online/ves/.json")
    fun getList(@Query("page") page: Int): Observable<DataResult>
}