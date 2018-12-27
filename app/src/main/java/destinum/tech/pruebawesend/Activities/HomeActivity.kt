package destinum.tech.pruebawesend.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import destinum.tech.pruebawesend.Data.Remote.LocalBitcoinsAPI
import destinum.tech.pruebawesend.R
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var provideAPI: LocalBitcoinsAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        App.component.inject(this)


        provideAPI.getList(1)
    }
}
