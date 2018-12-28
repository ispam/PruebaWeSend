package destinum.tech.pruebawesend.DI

import dagger.Component
import destinum.tech.pruebawesend.Activities.HomeActivity
import destinum.tech.pruebawesend.Activities.ResultActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, ViewModelModule::class))
interface AppComponent {

    fun inject(activity: HomeActivity)

    fun inject(activity: ResultActivity)
}