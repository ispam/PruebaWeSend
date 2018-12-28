package destinum.tech.pruebawesend.DI

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import destinum.tech.pruebawesend.BuildConfig
import destinum.tech.pruebawesend.Data.Remote.LocalBitcoinsAPI
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module(includes = arrayOf(AppModule::class))
class NetworkModule {

    companion object {
        const val BASE_URL = "https://localbitcoins.com/"
    }

    @Singleton @Provides
    fun provideAPI(retrofit: Retrofit): LocalBitcoinsAPI = retrofit.create(LocalBitcoinsAPI::class.java)

    @Singleton @Provides
    fun provideCache(cache: File): Cache = Cache(cache, 10*1024*1024)

    @Singleton @Provides
    fun provideFile(context: Context): File = File(context.cacheDir, "okhttp_cache")

    @Singleton @Provides
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.v("LogginInterceptor", it) })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return  interceptor
    }

    @Singleton @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder().client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    @Singleton @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient {

        val onlineInterceptor = Interceptor { chain ->
            try {
                val maxStale = 60 * 60 * 24
                val offlineRequest = chain.request().newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
                chain.proceed(offlineRequest)
            } catch (e: Exception) {
                chain.proceed(chain.request())
            }
        }

        return if (BuildConfig.DEBUG){
            OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(onlineInterceptor)
                .build()

        } else {
            OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(onlineInterceptor)
                .build()
        }
    }
}