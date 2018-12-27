package destinum.tech.pruebawesend.DI

import dagger.Component
import destinum.tech.pruebawesend.Activities.HomeActivity

@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(activity: HomeActivity)
}