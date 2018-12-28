package destinum.tech.pruebawesend.Activities

import android.app.Application
import android.content.Context
import destinum.tech.pruebawesend.DI.AppComponent
import destinum.tech.pruebawesend.DI.AppModule
import destinum.tech.pruebawesend.DI.DaggerAppComponent
import androidx.multidex.MultiDex



class App: Application() {

    companion object {
        @JvmStatic lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}