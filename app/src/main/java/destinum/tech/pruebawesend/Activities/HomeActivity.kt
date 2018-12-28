package destinum.tech.pruebawesend.Activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import destinum.tech.pruebawesend.Adapters.HomeAdapter
import destinum.tech.pruebawesend.Data.Remote.LocalBitcoinsAPI
import destinum.tech.pruebawesend.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    companion object {
        const val TAG = "HomeActivity"
    }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var provideAPI: LocalBitcoinsAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        App.component.inject(this)

        home_recycler.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        disposable.add(provideAPI.getList(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { home_recycler.adapter = HomeAdapter(it.data.ad_list) }
            .doOnError { Toast.makeText(this, "Something Happened, Try Again", Toast.LENGTH_LONG).show() }
            .doOnNext { Log.i(TAG, "Got first") }
            .subscribe())
    }

    override fun onStop() {
        if (!disposable.isDisposed) disposable.clear()
        super.onStop()
    }
}
