package destinum.tech.pruebawesend.DI

import dagger.Component
import destinum.tech.pruebawesend.Activities.HomeActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(activity: HomeActivity)
}