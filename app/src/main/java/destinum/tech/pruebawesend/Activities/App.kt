package destinum.tech.pruebawesend.Activities

import android.app.Application
import destinum.tech.pruebawesend.DI.AppComponent
import destinum.tech.pruebawesend.DI.AppModule
import destinum.tech.pruebawesend.DI.DaggerAppComponent

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

}